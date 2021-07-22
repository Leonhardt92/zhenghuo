package com.demo.service;

import com.demo.model.Form;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GifServiceTest {
    @Autowired
    GifService service;
    @Test
    public void renderGif() throws Exception {
        Form form = new Form();
        form.setTemplateName("sorry");
        form.setSentence("好啊5555,别说我是一等良民,就算你们真的想要诬告我,我有的是钱找律师帮我打官司,我想我根本不用坐牢,你别以为有钱了不起啊,sorry 有钱是真的能为所欲为的,不过我相信你不会明白这种感觉,不明白 不明白");
        form.setMode("simple");
        service.renderGif(form);
    }

    @Test
    public void renderAss() throws Exception {
        Form form = new Form();
        form.setTemplateName("sorry");
        form.setSentence("好啊zzz,别说我是一等良民,就算你们真的想要诬告我,我有的是钱找律师帮我打官司,我想我根本不用坐牢,你别以为有钱了不起啊,sorry 有钱是真的能为所欲为的,不过我相信你不会明白这种感觉,不明白 不明白");
        form.setMode("simple");
        String s = service.renderAss(form);
        System.out.println(s);
    }
}