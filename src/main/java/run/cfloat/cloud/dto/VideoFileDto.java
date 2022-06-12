package run.cfloat.cloud.dto;

import lombok.Builder;
import lombok.Data;

public class VideoFileDto {
    // 简单的视频格式
    @Builder
    @Data
    static public class Simple {
        public String path;
        public String name;
        public String createTime;
        public String thum; // 缩略图
    }
}
