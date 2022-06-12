package run.cfloat.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import run.cfloat.cloud.annotation.PassToken;
import run.cfloat.cloud.dto.RequestDto;
import run.cfloat.cloud.service.FileService;

@RestController
public class FileController extends Controller {
    @Autowired
    FileService service;

    @PassToken
    @GetMapping("/file/video")
    public Object getFileByPath(@Validated @RequestBody RequestDto.FileVideo params, BindingResult br) {
        final var err = checkParams(br);
        if (err != null) {
            return err;
        }
        // 全部视频
        final var videoList = service.getVideoList();
        // 分页后的视频
        final var videoPageing = service.getVideoByPage(params.getPage(), params.getPageSize(), videoList);
        final var result = service.getFullVideo(videoPageing);
        return toSuccess(result);
    }
}
