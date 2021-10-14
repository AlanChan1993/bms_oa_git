package com.infinitus.bms_oa.service.impl;


import com.infinitus.bms_oa.mapper.UserMapper;
import com.infinitus.bms_oa.pojo.User;
import com.infinitus.bms_oa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public User selectUser(Integer id) {
        return userMapper.selectUser(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

}
