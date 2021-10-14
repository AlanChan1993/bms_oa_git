package com.infinitus.bms_oa.controller;


import com.infinitus.bms_oa.pojo.Infinitus;
import com.infinitus.bms_oa.pojo.User;
import com.infinitus.bms_oa.service.BmsBillAdujestService;
import com.infinitus.bms_oa.service.InfinitusService;
import com.infinitus.bms_oa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/get")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private InfinitusService infinitusService;

    private Infinitus infinitus;

    @Autowired
    private BmsBillAdujestService bmsBillAdujestService;

    @RequestMapping("someThing")
    @ResponseBody
    public String getSomeThing(){
        return "Hello Word!";
    }

    @RequestMapping(value = "/findAll")
    @ResponseBody
    public List<User> findAll() {
        return userService.findAll();
    }

    @RequestMapping(value = "add")
    public int addUser() {
        User u = new User();
        int stat = userService.addUser(u);
        return stat;
    }

    @ResponseBody
    @RequestMapping("hello")
    public Infinitus hello(Integer id) {
        try {
            infinitus = infinitusService.getInfinitusById(id);
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return infinitus;
    }

    @RequestMapping("selectLoginNameById")
    public String selectLoginNameById(String id){
        return bmsBillAdujestService.selectLoginNameById(id);
    }
}
