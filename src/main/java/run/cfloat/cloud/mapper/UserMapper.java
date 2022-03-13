package run.cfloat.cloud.mapper;

import org.apache.ibatis.annotations.Mapper;

import run.cfloat.cloud.entity.User;

@Mapper
public interface UserMapper {
    User getUserByName(String name);

    void createUser(User user);
}
