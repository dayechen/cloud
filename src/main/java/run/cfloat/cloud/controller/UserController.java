package run.cfloat.cloud.controller;

import run.cfloat.cloud.annotation.PassToken;
import run.cfloat.cloud.dto.ReqDto;
import run.cfloat.cloud.service.UserService;

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
    public Object regiser(@Validated @RequestBody ReqDto.Regiser params, BindingResult br) {
        if (br.hasErrors()) {
            return toError(br.getFieldError().getDefaultMessage());
        }
        return toSuccess(service.create());
    }

    @PassToken
    @GetMapping(value = "/login")
    public Object login(@Validated @RequestBody ReqDto.Regiser params, BindingResult br) {
        return toSuccess();
    }

    @GetMapping(value = "/user/info")
    public Object getUserInfo(@RequestAttribute() String uid) {

        return toSuccess();
    }
}
