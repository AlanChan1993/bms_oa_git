package com.infinitus.bms_oa.service;



import com.infinitus.bms_oa.pojo.User;

import java.util.List;

public interface UserService {
    int addUser(User user);
    int deleteUser(Integer id);
    User selectUser(Integer id);
    int updateUser(User user);
    List<User> findAll();

}
