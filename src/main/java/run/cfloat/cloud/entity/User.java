package run.cfloat.cloud.entity;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Integer id;
    private String name;
    private String password;
    private Timestamp createTime;
}
