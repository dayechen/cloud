package run.cfloat.cloud.controller;

import run.cfloat.cloud.annotation.PassToken;
import run.cfloat.cloud.dto.RequestDto;
import run.cfloat.cloud.service.UserService;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends Controller {

    @Autowired
    UserService service;

    @PassToken
    @PostMapping(value = "/regiser")
    public Object regiser(@Validated @RequestBody RequestDto.Regiser params, BindingResult br) {
        final var msg = checkParams(br);
        if (msg != null) {
            return msg;
        }
        if (service.thereIs(params.getName())) {
            return toError("用户已经存在");
        }
        service.create(params);
        return toSuccess("创建成功");
    }

    @PassToken
    @GetMapping(value = "/login")
    public Object login(@Validated @RequestBody RequestDto.Regiser params, BindingResult br) {
        final var msg = checkParams(br);
        if (msg != null) {
            return msg;
        }
        final var user = service.getUserByName(params.getName());
        if (user == null) {
            return toError("用户不存在");
        }
        final var checkPass = service.checkPassword(params.getPassword(), user.getPassword());
        if (!checkPass) {
            return toError("用户名或密码错误");
        }
        final var token = service.generateToken(user.getId());
        final var result = new HashMap<>();
        result.put("token", token);
        return toSuccess(result);
    }

    @GetMapping(value = "/user/info")
    public Object getUserInfo(@RequestAttribute() String uid) {
        return toSuccess();
    }
}
