package com.demo.dao;

import com.demo.model.Vedio;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class VedioDaoTest {

    @Autowired
    VedioDao vedioDao;

    @Test
    public void countFaces() {
        int countFaces = vedioDao.countFaces("sorry");
        System.out.println(countFaces);
    }
}