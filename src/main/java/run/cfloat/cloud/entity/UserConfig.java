package run.cfloat.cloud.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserConfig {
    private String path;
    private String uid;
}