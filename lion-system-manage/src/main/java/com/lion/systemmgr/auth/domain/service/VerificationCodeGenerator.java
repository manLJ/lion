package com.lion.systemmgr.auth.domain.service;


import com.lion.systemmgr.auth.domain.model.VerifyCode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerificationCodeGenerator {

    /**
     * 验证码来源
     */
    final private char[] code = {
            '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    /**
     * 字体
     */
    final private String[] fontNames = new String[]{
            "黑体", "宋体", "Courier", "Arial",
            "Verdana", "Times", "Tahoma", "Georgia"};
    /**
     * 字体样式
     */
    final private int[] fontStyles = new int[]{
            Font.BOLD, Font.ITALIC | Font.BOLD
    };
    private VerifyCode verifyCode;

    public VerificationCodeGenerator() {
        this.verifyCode = new VerifyCode();
        this.generatorVCode();
    }

    /**
     * 指定验证码格式
     *
     * @param verifyCode 验证码格式
     */
    public VerificationCodeGenerator(VerifyCode verifyCode) {
        this.verifyCode = verifyCode;
        this.generatorVCode();
    }

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    private void generatorVCode() {
        int len = code.length;
        Random ran = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < this.verifyCode.getVcodeLen(); i++) {
            int index = ran.nextInt(len);
            sb.append(code[index]);
        }
        this.verifyCode.setCode(sb.toString());
    }

    public String verifyCode() {
        return this.verifyCode.getCode();
    }

    /**
     * 生成验证码图片
     *
     * @return
     */
    public BufferedImage generatorImage() {
        //创建验证码图片
        BufferedImage vcodeImage = new BufferedImage(this.verifyCode.getWidth(), this.verifyCode.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = vcodeImage.getGraphics();
        //填充背景色
        g.setColor(new Color(246, 240, 250));
        g.fillRect(0, 0, this.verifyCode.getWidth(), this.verifyCode.getHeight());
        //添加干扰线
        drawDisturbLine(g);
        //用于生成伪随机数
        Random ran = new Random();
        //在图片上画验证码
        for (int i = 0; i < this.verifyCode.getCode().length(); i++) {
            //设置字体
            g.setFont(new Font(fontNames[ran.nextInt(fontNames.length)], fontStyles[ran.nextInt(fontStyles.length)], this.verifyCode.getFontsize()));
            //随机生成颜色
            g.setColor(getRandomColor());
            //画验证码
            g.drawString(this.verifyCode.getCode().charAt(i) + "", i * this.verifyCode.getFontsize() + 10, this.verifyCode.getFontsize() + 5);
        }
        //释放此图形的上下文以及它使用的所有系统资源
        g.dispose();

        return vcodeImage;
    }

    /**
     * 获得旋转字体的验证码图片
     *
     * @return
     */
    public BufferedImage generatorRotateImage() {
        //创建验证码图片
        BufferedImage rotateVcodeImage = new BufferedImage(this.verifyCode.getWidth(), this.verifyCode.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = rotateVcodeImage.createGraphics();
        //填充背景色
        g2d.setColor(new Color(246, 240, 250));
        g2d.fillRect(0, 0, this.verifyCode.getWidth(), this.verifyCode.getHeight());
        //添加干扰线
        drawDisturbLine(g2d);
        //在图片上画验证码
        for (int i = 0; i < this.verifyCode.getCode().length(); i++) {
            BufferedImage rotateImage = getRotateImage(this.verifyCode.getCode().charAt(i));
            g2d.drawImage(rotateImage, null, (int) (this.verifyCode.getHeight() * 0.7) * i, 0);
        }
        g2d.dispose();
        return rotateVcodeImage;
    }

    /**
     * 为验证码图片画一些干扰线
     *
     * @param g
     */
    private void drawDisturbLine(Graphics g) {
        Random ran = new Random();
        for (int i = 0; i < this.verifyCode.getVcodeLen(); i++) {
            int x1 = ran.nextInt(this.verifyCode.getWidth());
            int y1 = ran.nextInt(this.verifyCode.getHeight());
            int x2 = ran.nextInt(this.verifyCode.getWidth());
            int y2 = ran.nextInt(this.verifyCode.getHeight());
            g.setColor(getRandomColor());
            //画干扰线
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 获取一张旋转的图片
     *
     * @param c 要画的字符
     * @return
     */
    private BufferedImage getRotateImage(char c) {
        BufferedImage rotateImage = new BufferedImage(this.verifyCode.getHeight(), this.verifyCode.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotateImage.createGraphics();
        //设置透明度为0
        g2d.setColor(new Color(255, 255, 255, 0));
        g2d.fillRect(0, 0, this.verifyCode.getHeight(), this.verifyCode.getHeight());
        Random ran = new Random();
        g2d.setFont(new Font(fontNames[ran.nextInt(fontNames.length)], fontStyles[ran.nextInt(fontStyles.length)], this.verifyCode.getFontsize()));
        g2d.setColor(getRandomColor());
        double theta = getTheta();
        //旋转图片
        g2d.rotate(theta, this.verifyCode.getHeight() / 2, this.verifyCode.getHeight() / 2);
        g2d.drawString(Character.toString(c), (this.verifyCode.getHeight() - this.verifyCode.getFontsize()) / 2, this.verifyCode.getFontsize() + 5);
        g2d.dispose();

        return rotateImage;
    }

    /**
     * @return 返回一个随机颜色
     */
    private Color getRandomColor() {
        Random ran = new Random();
        return new Color(ran.nextInt(220), ran.nextInt(220), ran.nextInt(220));
    }

    /**
     * @return 角度
     */
    private double getTheta() {
        return ((int) (Math.random() * 1000) % 2 == 0 ? -1 : 1) * Math.random();
    }


}
