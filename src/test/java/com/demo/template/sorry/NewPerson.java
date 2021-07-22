package com.demo.template.sorry;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.youtu.Youtu;
import org.junit.Test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;


public class NewPerson {
    // appid, secretid secretkey请到http://open.youtu.qq.com/获取
    // 请正确填写把下面的APP_ID、SECRET_ID和SECRET_KEY
    public static final String APP_ID = "10119632";
    public static final String SECRET_ID = "AKID8W5XouLX4Qahdd7KxRjpkU9h8FF3N5JL";
    public static final String SECRET_KEY = "5Xvb8tX0bS9g4GMH2e5OWTm5GF7nIhvy";
    public static final String USER_ID = "1026742642"; //qq号

    public static final Youtu FACEYOUTU = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);

    public static final String PERSONIDS = "sorry";

    @Test
    public void newBoss() throws NoSuchAlgorithmException, IOException, KeyManagementException {
        LinkedList<String> sorry = new LinkedList<>();
        sorry.add(PERSONIDS);
        JSONObject boss = FACEYOUTU.NewPerson("E:\\IdeaProjects\\sqlite\\src\\test\\resources\\template\\sorry\\person\\boss\\boss.jpg", "boss", sorry);
        System.out.println(JSON.toJSONString(boss, true));
        LinkedList<String> bossImage = new LinkedList<>();
        bossImage.add("E:\\IdeaProjects\\sqlite\\src\\test\\resources\\template\\sorry\\person\\boss\\boss.jpg");
        bossImage.add("E:\\IdeaProjects\\sqlite\\src\\test\\resources\\template\\sorry\\person\\boss\\boss1.jpg");
        bossImage.add("E:\\IdeaProjects\\sqlite\\src\\test\\resources\\template\\sorry\\person\\boss\\boss2.jpg");
        JSONObject addFace = FACEYOUTU.AddFace("boss", bossImage);
        System.out.println(JSON.toJSONString(addFace, true));
    }

    @Test
    public void delBoss() throws NoSuchAlgorithmException, IOException, KeyManagementException {
        JSONObject delStaff = FACEYOUTU.DelPerson("boss");
        System.out.println(JSON.toJSONString(delStaff, true));
    }

    @Test
    public void newStaff() throws NoSuchAlgorithmException, IOException, KeyManagementException {
        LinkedList<String> sorry = new LinkedList<>();
        sorry.add(PERSONIDS);
        JSONObject boss = FACEYOUTU.NewPerson("E:\\IdeaProjects\\sqlite\\src\\test\\resources\\template\\sorry\\person\\staff\\staff.jpg", "staff", sorry);
        System.out.println(JSON.toJSONString(boss, true));
        LinkedList<String> bossImage = new LinkedList<>();
        bossImage.add("src\\test\\resources\\template\\sorry\\person\\staff\\staff.jpg");
        bossImage.add("src\\test\\resources\\template\\sorry\\person\\staff\\staff1.jpg");
        bossImage.add("src\\test\\resources\\template\\sorry\\person\\staff\\staff2.jpg");
        bossImage.add("src\\test\\resources\\template\\sorry\\person\\staff\\staff3.jpg");
        bossImage.add("src\\test\\resources\\template\\sorry\\person\\staff\\staff4.jpg");
        // bossImage.add("src\\test\\resources\\template\\sorry\\person\\staff\\staff5.jpg");
        JSONObject addFace = FACEYOUTU.AddFace("staff", bossImage);
        System.out.println(JSON.toJSONString(addFace, true));
    }

    @Test
    public void delStaff() throws NoSuchAlgorithmException, IOException, KeyManagementException {
        JSONObject delStaff = FACEYOUTU.DelPerson("staff");
        System.out.println(JSON.toJSONString(delStaff, true));
    }


    @Test
    public void newPerson() throws NoSuchAlgorithmException, IOException, KeyManagementException {
        LinkedList<String> sorry = new LinkedList<>();
        sorry.add(PERSONIDS);
        JSONObject boss = FACEYOUTU.NewPerson("E:\\IdeaProjects\\sqlite\\src\\test\\resources\\template\\sorry\\person\\person\\person.jpg", "person", sorry);
        System.out.println(JSON.toJSONString(boss, true));
        LinkedList<String> bossImage = new LinkedList<>();
        bossImage.add("src\\test\\resources\\template\\sorry\\person\\person\\person.jpg");
        bossImage.add("src\\test\\resources\\template\\sorry\\person\\person\\person1.jpg");
        JSONObject addFace = FACEYOUTU.AddFace("person", bossImage);
        System.out.println(JSON.toJSONString(addFace, true));
    }

    @Test
    public void delPerson() throws NoSuchAlgorithmException, IOException, KeyManagementException {
        JSONObject delStaff = FACEYOUTU.DelPerson("person");
        System.out.println(JSON.toJSONString(delStaff, true));
    }


    @Test
    public void getPersons() throws NoSuchAlgorithmException, IOException, KeyManagementException {
        JSONObject sorry = FACEYOUTU.GetPersonIds(PERSONIDS);
        System.out.println(JSON.toJSONString(sorry, true));
    }
}
