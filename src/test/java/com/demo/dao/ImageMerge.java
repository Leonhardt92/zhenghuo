package com.demo.dao;


import org.junit.Test;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

/**

 * 该类实现了图片的合并功能，可以选择水平合并或者垂直合并。
 * 当然此例只是针对两个图片的合并，如果想要实现多个图片的合并，只需要自己实现方法 BufferedImage
 * mergeImage(BufferedImage[] imgs, boolean isHorizontal)即可；
 * 而且这个方法更加具有通用性，但是时间原因不实现了，方法和两张图片实现是一样的
 */

public class ImageMerge {



    @Test
    public void watermark() throws IOException {
        File file = new File("src\\test\\resources\\image\\bg.png");
        File waterFile=new File("src\\test\\resources\\image\\watermark.png");
         float alpha = 1f;
        // 获取底图
        BufferedImage buffImg = ImageIO.read(file);
        //获取底图的宽度
        int buffImgWidth = buffImg.getWidth();
        //获取底图的高度
        int buffImgHight = buffImg.getHeight();

        // 获取层图1
        BufferedImage waterImg1 = ImageIO.read(waterFile);
        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = buffImg.createGraphics();
        // 获取层图的宽度
        int waterImgWidth1 = waterImg1.getWidth();
        // 获取层图的高度
        int waterImgHeight1 = waterImg1.getHeight();
        // 在图形和图像中实现混合和透明效果
        // 当底图和水印图片都是png时，不要开启混合透明效果，否则可能导致水印图片被底图部分遮盖
        //g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        // 绘制
        //g2d.drawImage(waterImg1, 775,  450, waterImgWidth1, waterImgHeight1, null);
        g2d.drawImage(waterImg1, 755,  340, 350, 430, null);


        g2d.dispose();// 释放图形上下文使用的系统资源
        ImageIO.write(buffImg, "png", new File("src\\test\\resources\\output\\watermark.png"));
    }

    @Test
    public void watermarkAndRoate() throws IOException {
        File file = new File("src\\test\\resources\\image\\bg.png");
        File waterFile=new File("src\\test\\resources\\image\\watermark.png");
        // 获取底图
        BufferedImage buffImg = ImageIO.read(file);

        // 获取层图1
        BufferedImage waterImg1 = ImageIO.read(waterFile);
        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = buffImg.createGraphics();
        double rotationRequired = Math.toRadians (75);
        double locationX = waterImg1.getWidth() / 2;
        double locationY = waterImg1.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g2d.drawImage(op.filter(waterImg1, null), 755,  340, 350, 430, null);


        g2d.dispose();// 释放图形上下文使用的系统资源
        ImageIO.write(buffImg, "png", new File("src\\test\\resources\\output\\waterAndRoate.png"));
    }

    @Test
    public void getSystem() {
        System.out.println(System.getProperty("os.name"));
    }
}