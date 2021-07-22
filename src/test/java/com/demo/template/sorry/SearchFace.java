package com.demo.template.sorry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.youtu.Youtu;
import com.demo.youtu.model.CandidatesItem;
import com.demo.youtu.model.FaceRect;
import com.demo.youtu.model.ResultsItem;
import com.demo.youtu.model.YoutuResponse;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchFace {
    // appid, secretid secretkey请到http://open.youtu.qq.com/获取
    // 请正确填写把下面的APP_ID、SECRET_ID和SECRET_KEY
    public static final String APP_ID = "10119632";
    public static final String SECRET_ID = "AKID8W5XouLX4Qahdd7KxRjpkU9h8FF3N5JL";
    public static final String SECRET_KEY = "5Xvb8tX0bS9g4GMH2e5OWTm5GF7nIhvy";
    public static final String USER_ID = "1026742642"; //qq号

    public static final Youtu FACEYOUTU = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);

    public static final String PERSONIDS = "sorry";

    @Test
    public void multifaceidentify() throws NoSuchAlgorithmException, IOException, KeyManagementException {
        LinkedList<String> sorry = new LinkedList<>();
        sorry.add(PERSONIDS);

        List<String> paths = new ArrayList<>();
        Path start = new File("src\\test\\resources\\template\\sorry\\image").toPath();
        try (Stream<Path> pathStream = Files.walk(start)) {
            paths = pathStream
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .map(File::getAbsolutePath)
                    .filter(e->e.endsWith("jpg"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String path : paths) {
            JSONObject result = FACEYOUTU.MultiFaceIdentify(path, "", sorry);
            String jsonString = JSON.toJSONString(result, true);
            System.out.println(jsonString);
            try (PrintWriter out = new PrintWriter(path.replace("jpg","json"))) {
                out.println(jsonString);
            }
        }
    }

    @Test
    public void multifaceidentifyOne() throws NoSuchAlgorithmException, IOException, KeyManagementException {
        LinkedList<String> sorry = new LinkedList<>();
        sorry.add(PERSONIDS);
        JSONObject result = FACEYOUTU.MultiFaceIdentify("src\\test\\resources\\template\\sorry\\image\\sorry_339.jpg", "", sorry);
        System.out.println(JSON.toJSONString(result, true));
    }

}
