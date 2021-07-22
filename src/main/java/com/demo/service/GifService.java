package com.demo.service;

import com.demo.model.Form;
import com.google.common.base.Splitter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by zhanzz on 2021/7/19
 */
@Service
public class GifService {

    @Autowired
    private VedioService vedioService;

    @Autowired
    ResourceLoader resourceLoader;


    private static final Logger logger = LoggerFactory.getLogger(GifService.class);
    @Value("${cache.path}")
    private String cachePath;
    @Value("${template.path}")
    private String templatePath;

    public String renderGif(Form form) throws Exception {
        String videoPath = renderImage(form);
        String assPath = renderAss(form);
        String gifPath = Paths.get(cachePath).resolve(UUID.randomUUID() + ".gif").toString();
        String assTemp = assPath;
        // 如果是window系统做特殊处理
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            assTemp = assTemp.replaceAll("\\\\", "\\\\\\\\").replace(":", "\\:");
            assTemp = "'" + assTemp + "'";
        }
        String cmd = String.format("ffmpeg -f image2 -framerate 6 -i %s/%%03d.jpg -vf ass=%s,scale=300:-1 -y %s",
                videoPath, assTemp, gifPath);
        if ("simple".equals(form.getMode())) {
            cmd = String.format("ffmpeg -f image2 -framerate 6 -i %s/%%03d.jpg -vf ass=%s,scale=180:-1 -y %s ", videoPath, assTemp, gifPath);
        }
        logger.info("cmd: {}", cmd);
        try {
            Process exec = null;
            if(osName.toLowerCase().contains("linux")){
                exec = Runtime.getRuntime().exec(new String[]{"sh","-c",cmd});
            }else if(osName.toLowerCase().contains("windows")){
                exec = Runtime.getRuntime().exec(cmd);
            }else{
                logger.warn("未知的操作系统：{}",osName);
            }
            logger.info("ffmpeg copy cmd is: {}",cmd);
            printProcessMsg(exec);
            exec.waitFor();
            // FileUtils.deleteDirectory(new File(videoPath));
            // FileUtils.deleteQuietly(new File(assPath));
           // logger.info("输出:{}",IOUtils.toString(exec.getErrorStream()));
        } catch (Exception e) {
            logger.error("生成gif报错：{}", e);
        }
        return gifPath;
    }


    public String renderAss(Form form) throws Exception {
        Path path = Paths.get(cachePath).resolve(UUID.randomUUID().toString().replace("-", "") + ".ass");
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setDirectoryForTemplateLoading(Paths.get(templatePath).resolve(form.getTemplateName()).toFile());
        Map<String, Object> root = new HashMap<>();
        Map<String, String> mx = new HashMap<>();
        List<String> list = Splitter.on(",").splitToList(form.getSentence());
        for (int i = 0; i < list.size(); i++) {
            mx.put("sentences" + i, list.get(i));
        }
        root.put("mx", mx);
        Template temp = cfg.getTemplate("template.ftl");
        try (FileWriter writer = new FileWriter(path.toFile())) {
            temp.process(root, writer);
        } catch (Exception e) {
            logger.error("生成ass文件报错", e);
        }
        return path.toString();
    }

    private String renderImage(Form form) throws IOException {
        Path path = Paths.get(cachePath).resolve(UUID.randomUUID().toString());
        String savePath = path.toString();
        File directory = new File(savePath);
        directory.mkdirs();
        HashMap hashMap = new HashMap();
        List<MultipartFile> images = form.getImageList();
        if (images != null && images.size() > 0) {
            for (int i = 0; i < images.size(); i++) {
                hashMap.put(String.valueOf(i), images.get(i).getInputStream());
            }
        }
        vedioService.addFaceBuffer(form.getTemplateName(), hashMap, form.getTemplateName(), savePath);
        return savePath;
    }

    /**
     * 处理process输出流和错误流，防止进程阻塞，在process.waitFor();前调用
     * @param exec
     * @throws IOException
     */
    private void printProcessMsg(Process exec) throws IOException {
        //防止ffmpeg进程塞满缓存造成死锁
        InputStream error = exec.getErrorStream();
        InputStream is = exec.getInputStream();

        StringBuffer result = new StringBuffer();
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(error,"GBK"));
            BufferedReader br2 = new BufferedReader(new InputStreamReader(is,"GBK"));

            while((line = br.readLine()) != null){
                result.append(line+"\n");
            }
            logger.info("FFMPEG视频转换进程错误信息："+result.toString());

            result = new StringBuffer();
            line = null;

            while((line = br2.readLine()) != null){
                result.append(line+"\n");
            }
        }catch (IOException e2){
            e2.printStackTrace();
        }finally {
            error.close();
            is.close();
        }

    }


}
