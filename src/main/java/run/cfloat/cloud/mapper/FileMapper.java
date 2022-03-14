package run.cfloat.cloud.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    String getUserPath(String uid);
}
