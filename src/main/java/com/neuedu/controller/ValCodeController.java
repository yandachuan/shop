package com.neuedu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
public class ValCodeController {
    @RequestMapping("/valCode")
    public void valCode(HttpSession session, HttpServletResponse response) throws IOException {

        //设置内容输出类型为jpeg图片
        response.setContentType("image/jpeg");

        //清除缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");

        //*************生成验证码答案****************

        //指定验证码内容的数据范围   可根据需要修改
        //String valcodeScope = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ指定验证码内容的数据范围 abcdefghijklmnpqrstuvwxyz";
        String valcodeScope = "123456789";

        //验证码长度为4个字符    可根据需要修改
        int length = 4;

        //指定验证码图片的大小    可根据需要修改
        int width = 80;
        int height = 25;

        //产生随机对象----参考Random类
        Random rd = new Random();

        //生成验证码的字符串
        String valcodeAnswer = "";

        for (int i = 0; i < length; i++) {
            valcodeAnswer += valcodeScope.charAt(rd.nextInt(valcodeScope.length()));
        }

        //将验证码答案保存在session属性范围中
        session.setAttribute("valcodeAnswer", valcodeAnswer);

        //*************绘制验证码图片****************

        //创建画布
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //从画布上获取画笔
        Graphics g = img.getGraphics();

        //画出底色为浅灰色的矩形   可根据需要修改
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, width, height);

        //画出10条干扰线，颜色随机，位置随机   可根据需要修改
        for (int i = 0; i < 10; i++) {
            g.setColor(new Color(rd.nextInt(255), rd.nextInt(255), rd.nextInt(255)));
            g.drawLine(rd.nextInt(width), rd.nextInt(height), rd.nextInt(width), rd.nextInt(height));
        }

        Font[] fonts = {new Font("宋体", Font.BOLD, 20), new Font("隶书", Font.BOLD, 18), new Font("黑体", Font.BOLD, 22)};

        //画出验证码的每个字符
        for (int i = 0; i < length; i++) {

            //随机设置颜色   可根据需要修改
            g.setColor(new Color(rd.nextInt(100), rd.nextInt(100), rd.nextInt(100)));

            //随机设置字体   可根据需要修改
            g.setFont(fonts[rd.nextInt(fonts.length)]);

            //旋转文字    可根据需要修改
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform trans = new AffineTransform();
            trans.rotate(rd.nextInt(45) * 3.14 / 180, 15 * i + 10, 7);
            g2d.setTransform(trans);

            //画出单个字符    随机设置字符高度  可根据需要修改
            g.drawString(valcodeAnswer.charAt(i) + "", width * i / length + 5, rd.nextInt(5) + 15);
        }

        //释放画笔
        g.dispose();

        //生成图片
        ImageIO.write(img, "JPEG", response.getOutputStream());


    }
}
