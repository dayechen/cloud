package run.cfloat.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import run.cfloat.cloud.dto.RequestDto;
import run.cfloat.cloud.dto.VideoFileDto.Simple;
import run.cfloat.cloud.service.FileService;

@RestController
@CrossOrigin
public class FileController extends Controller {
    @Autowired
    FileService service;

    @PostMapping("/file/video")
    public Object getFileByPath(@Validated @RequestBody RequestDto.FileVideo params, BindingResult br) {
        final var err = checkParams(br);
        if (err != null) {
            return err;
        }
        // 全部视频
        List<Simple> videoList = service.getVideoList();
        List<Simple> videoPageing;

        if (params.getRandom()) {
            videoPageing = service.getRandomVideo(params.getPageSize(), videoList);
        } else {
            videoPageing = service.getVideoByPage(params.getPage(), params.getPageSize(), videoList);
        }
        // 分页后的视频
        final var result = service.getFullVideo(videoPageing);
        return toSuccess(result);
    }

    @PostMapping("/file/thum")
    public Object buildThum() {
        new Thread(() -> {
            List<Simple> videoList = service.getVideoList();
            service.getFullVideo(videoList);
        }).start();
        return toSuccess("成功");
    }
}
