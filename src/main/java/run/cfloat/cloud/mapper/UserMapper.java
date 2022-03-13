package run.cfloat.cloud.mapper;

import org.apache.ibatis.annotations.Mapper;

import run.cfloat.cloud.entity.User;
import run.cfloat.cloud.entity.UserConfig;

@Mapper
public interface UserMapper {
    User getUserByName(String name);

    User getUserById(String id, String... col);

    void createUser(User user);

    void updateConfig(UserConfig config);
}
