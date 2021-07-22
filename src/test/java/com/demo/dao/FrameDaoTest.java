package com.demo.dao;

import com.alibaba.fastjson.JSON;
import com.demo.model.FaceSO;
import com.demo.model.Frame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class FrameDaoTest {

    @Autowired
    private FrameDao frameDao;

    @Test
    public void insert() {
        Frame frame = new Frame();
        frame.setPath("c:\\temp");
        frameDao.insert(frame);
        System.out.println(JSON.toJSONString(frame, true));
    }

    @Test
    public void findFaces() {
        List<FaceSO> faces = frameDao.findFaces(5);
        System.out.println(JSON.toJSONString(faces, true));
    }
}