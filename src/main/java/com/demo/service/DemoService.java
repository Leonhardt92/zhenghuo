package com.demo.service;

import com.demo.dao.DemoDao;
import com.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    @Autowired
    private DemoDao demoDao;

    public User selectUser(int id) {
        User user = demoDao.select(id);
        return user;
    }

    public void insertuser(User user) {
        demoDao.insert(user);
    }
}
