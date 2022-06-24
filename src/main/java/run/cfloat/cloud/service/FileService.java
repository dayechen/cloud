package run.cfloat.cloud.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import run.cfloat.cloud.dto.VideoFileDto;
import run.cfloat.cloud.mapper.FileMapper;

@Service
public class FileService {
    @Autowired
    FileMapper mapper;

    // 获取所有电影的列表
    private List<String> getPathList() {
        final var result = new ArrayList<String>();
        result.add("/home/cddy/store/no1/movie/yellow");
        result.add("/home/cddy/store/no2/movie/yellow");
        return result;
    }

    // 获取文件创建时间
    private String getFileCreateTime(String filePath) {
        try {
            final var dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            final var t = Files.readAttributes(Paths.get(filePath), BasicFileAttributes.class).creationTime();
            return dateFormat.format(t.toMillis());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // 截取视频某一帧
    private String getVideoFps(File file, int fps) {
        final var ffmpeg = new FFmpegFrameGrabber(file);
        try {
            ffmpeg.start();
            if (fps > ffmpeg.getLengthInFrames()) {
                ffmpeg.close();
                return "";
            }

            final var fileName = file.getName().substring(0, file.getName().lastIndexOf(".")) + "_f" + fps + ".png";
            final var pathName = "/home/cddy/store/image/yellow/" + fileName ;
            // 查找文件是否存在
            final var outputFile = new File(pathName);
            if (outputFile.exists()) {
                ffmpeg.close();
                return "/image/" + fileName;
            }
            Frame frame = null;
            for (var i = 0; i < fps; i++) {
                frame = ffmpeg.grabImage();
                if (i == fps) {
                    break;
                }
            }
            final var converter = new Java2DFrameConverter();
            final var imgSrc = converter.getBufferedImage(frame);
            // final var stream = new ByteArrayOutputStream();
            ImageIO.write(imgSrc, "png", outputFile);
            // final var base64 = Base64.getEncoder().encodeToString(stream.toByteArray());
            converter.close();
            ffmpeg.close();
            // return "data:image/jpg;base64," + base64;
            return "/image/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 获取全部视频列表
    public List<VideoFileDto.Simple> getVideoList() {
        final var result = new ArrayList<VideoFileDto.Simple>();
        // 所有的视频列表
        getPathList().forEach(x -> {
            for (var y : new File(x).listFiles()) {
                final var item = VideoFileDto.Simple.builder()
                        .path(y.getPath())
                        .name(y.getName())
                        .createTime(getFileCreateTime(y.getPath()))
                        .build();
                result.add(item);
            }
        });
        return result.stream().sorted(new Comparator<VideoFileDto.Simple>() {
            @Override
            public int compare(VideoFileDto.Simple p1, VideoFileDto.Simple p2) {
                try {
                    final var sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                    final var d1 = sdf.parse(p1.createTime);
                    final var d2 = sdf.parse(p2.createTime);
                    if (d1.getTime() > d2.getTime()) {
                        return -1;
                    }
                    if (d1.getTime() == d2.getTime()) {
                        return 0;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 1;
            }
        }).toList();
    }

    // 获取视频分页
    public List<VideoFileDto.Simple> getVideoByPage(int page, int pageSize, List<VideoFileDto.Simple> list) {
        page -= 1;
        final var result = new ArrayList<VideoFileDto.Simple>();
        final var startIdx = page * pageSize;
        final var endIdx = startIdx + pageSize;
        for (var i = startIdx; i < endIdx; i++) {
            final var item = list.get(i);
            if (item == null) {
                return result;
            }
            result.add(item);
        }
        return result;
    }

    public List<VideoFileDto.Simple> getRandomVideo(int size, List<VideoFileDto.Simple> list) {
        final var result = new ArrayList<VideoFileDto.Simple>();
        for (int i = 0; i < size; i++) {
            result.add(list.get(getRandom(0, list.size())));
        }
        return result;
    }

    /**
     * 生成随机数组
     * 
     * @param size 目标数组大小
     * @param max  目标数最大值
     */
    public int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    // 获取完整的视频信息
    public List<VideoFileDto.Simple> getFullVideo(List<VideoFileDto.Simple> list) {
        final var result = new ArrayList<VideoFileDto.Simple>();
        list.forEach(x -> {
            x.thum = getVideoFps(new File(x.path), 9999);
            result.add(x);
        });
        return result;
    }
}
