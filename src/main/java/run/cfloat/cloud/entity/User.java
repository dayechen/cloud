package run.cfloat.cloud.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Integer id;
    private String username;
    private String password;
}
