package com.demo.service;

import com.demo.dao.FaceDao;
import com.demo.dao.FrameDao;
import com.demo.dao.VedioDao;
import com.demo.model.Face;
import com.demo.model.FaceSO;
import com.demo.model.Frame;
import com.demo.model.Vedio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

@Service
public class VedioService {

    @Autowired
    private FrameDao frameDao;
    @Autowired
    private FaceDao faceDao;
    @Autowired
    private VedioDao vedioDao;

    private Map<String, BufferedImage> faceBuffImage = new HashMap<>();
    @Value("${template.path}")
    private String templatePath;
    private static final Logger logger = LoggerFactory.getLogger(VedioService.class);


    public void init(String templateName, Map<String, String> faces) throws IOException {
        faceBuffImage.clear();
        for (Map.Entry<String, String> entry : faces.entrySet()) {
            faceBuffImage.put(templateName + entry.getKey(), ImageIO.read(new File(entry.getValue())));
        }
    }

    public void addFaceBuffer(String templateName, Map<String, InputStream> faces, String vedioName, String savePath) throws IOException {
        faceBuffImage.clear();
        for (Map.Entry<String, InputStream> entry : faces.entrySet()) {
            faceBuffImage.put(templateName + entry.getKey(), ImageIO.read(entry.getValue()));
        }
        Vedio vedio = vedioDao.selectByName(vedioName);
        addFace(vedio.getId(), savePath);
    }

    public void addFaceStr(Map<String, String> faces, int vedioId, String savePath) throws IOException {
        faceBuffImage.clear();
        for (Map.Entry<String, String> entry : faces.entrySet()) {
            faceBuffImage.put(entry.getKey(), ImageIO.read(new File(entry.getValue())));
        }
        addFace(vedioId, savePath);
    }


    private void addFace(int vedioId, String savePath) {
        List<FaceSO> faces = frameDao.findFaces(vedioId);
        // 并行处理
        faces.parallelStream()
                .forEach(faceSO->{
                    try {
                        BufferedImage backBuffer = ImageIO.read(new File(templatePath+faceSO.getPath()));
                        BufferedImage bufferedImage = addFace(backBuffer, faceSO.getFaces());
                        int index = faceSO.getPath().lastIndexOf("/");
                        ImageIO.write(bufferedImage, "jpg", new File(savePath + faceSO.getPath().substring(index)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }


    public BufferedImage addFace(BufferedImage backgroundImage, List<Face> frameFaces) {
        Graphics2D g2d = backgroundImage.createGraphics();
        for (Face face : frameFaces) {
            String id = face.getId();
            BufferedImage bufferedImage = faceBuffImage.get(id);
            if (bufferedImage != null) {
                double rotationRequired = Math.toRadians(face.getRotation());
                double locationX = bufferedImage.getWidth() / 2;
                double locationY = bufferedImage.getHeight() / 2;
                AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                g2d.drawImage(op.filter(bufferedImage, null), (int) face.getLeft(), (int) face.getTop(), (int) face.getWidth(), (int) face.getHeight(), null);
            }
        }
        g2d.dispose();
        return backgroundImage;
    }




}
