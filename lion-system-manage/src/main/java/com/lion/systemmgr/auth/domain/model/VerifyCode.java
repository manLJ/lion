package com.lion.systemmgr.auth.domain.model;

/**
 * Created by JACK on 2015/9/9.
 */
public class VerifyCode {

    /**
     * 验证码长度
     * 默认4个字符
     */
    private int vcodeLen = 4;
    /**
     * 验证码图片字体大小
     * 默认17
     */
    private int fontsize = 21;
    /**
     * 验证码图片宽度
     */
    private int width = (fontsize+1)*vcodeLen+10;
    /**
     * 验证码图片高度
     */
    private int height = fontsize+12;
    /**
     * 干扰线条数
     * 默认3条
     */
    private int disturbline = 3;
    private String code;

    /**
     * @return 验证码字符个数
     */
    public int getVcodeLen() {
        return vcodeLen;
    }
    /**
     * 设置验证码字符个数
     * @param vcodeLen
     */
    public void setVcodeLen(int vcodeLen) {
        this.width = (fontsize+3)*vcodeLen+10;
        this.vcodeLen = vcodeLen;
    }
    /**
     * @return 字体大小
     */
    public int getFontsize() {
        return fontsize;
    }
    /**
     * 设置字体大小
     * @param fontsize
     */
    public void setFontsize(int fontsize) {
        this.width = (fontsize+3)*vcodeLen+10;
        this.height = fontsize+15;
        this.fontsize = fontsize;
    }
    /**
     * @return 图片宽度
     */
    public int getWidth() {
        return width;
    }
    /**
     * 设置图片宽度
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }
    /**
     * @return 图片高度
     */
    public int getHeight() {
        return height;
    }
    /**
     * 设置图片高度
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }
    /**
     * @return 干扰线条数
     */
    public int getDisturbline() {
        return disturbline;
    }
    /**
     * 设置干扰线条数
     * @param disturbline
     */
    public void setDisturbline(int disturbline) {
        this.disturbline = disturbline;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
