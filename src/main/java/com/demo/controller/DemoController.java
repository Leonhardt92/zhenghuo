package com.demo.controller;

import com.demo.dao.DemoDao;
import com.demo.model.User;
import com.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

// @RestController
public class DemoController {
    @Autowired
    private DemoService demoService;


    @Autowired
    private DemoDao demoDao;

    @GetMapping(value = "/findUserById")
    public User selectUser(int id) {
        User user = demoService.selectUser(id);
        return user;
    }

    @PostMapping(value = "/addUser")
    @ResponseBody
    void insert(@RequestBody User user){
        demoService.insertuser(user);
    }

    @PostMapping(value = "/changeUser")
    @ResponseBody
    void update(@RequestBody User user){
        demoDao.update(user);
    }

    @GetMapping(value = "/remove")
    void delete(int id){
        demoDao.delete(id);
    }
}
