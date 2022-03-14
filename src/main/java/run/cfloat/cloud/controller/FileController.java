package run.cfloat.cloud.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import run.cfloat.cloud.service.FileService;

@RestController
public class FileController extends Controller {
    @Autowired
    FileService service;

    @GetMapping("/file/path")
    public Object getFileByPath(@RequestAttribute() String uid, String path) {
        final var basePath = service.getBasePath(uid);
        final var files = service.readFile(basePath);
        final var result = new HashMap<>();
        result.put("list", files);
        return result;
    }
}
