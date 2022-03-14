package run.cfloat.cloud.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import run.cfloat.cloud.mapper.FileMapper;

@Service
public class FileService {
    @Autowired
    FileMapper mapper;

    public String getBasePath(String uid) {
        return mapper.getUserPath(uid);
    }

    public List<String> readFile(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
                // 这里就不递归了，
            }
        }
        return files;
    }
}
