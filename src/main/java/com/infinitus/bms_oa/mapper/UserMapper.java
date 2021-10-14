package com.infinitus.bms_oa.mapper;


import com.infinitus.bms_oa.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int insertUser(User user);
    int deleteUser(Integer id);
    User selectUser(Integer id);
    int updateUser(User user);
    List<User> selectAll();
}
