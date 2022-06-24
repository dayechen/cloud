package run.cfloat.cloud.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

public class RequestDto {
    @Data
    static public class Regiser {
        @NotNull(message = "用户名不能为空")
        private String name;
        @NotNull(message = "密码不能为空")
        @Length(min = 6, max = 20, message = "密码最小6位，最大20位")
        private String password;
    }

    @Data
    static public class Config {
        @NotNull(message = "路径不能为空")
        private String path;
    }

    @Data
    static public class FileVideo {
        @NotNull(message = "页码不能为空")
        private int page;

        @NotNull(message = "页码范围不能为空")
        private int pageSize;

        @NotNull(message = "是否随机")
        private Boolean random;
    }
}
