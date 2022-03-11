package run.cfloat.cloud.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String create() {
        return "这里是创建用户";
    }

    // 判断用户是否存在
    public boolean thereIs(String username) {
        return false;
    }
}
