package com.uxin.recy.entity;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2018/10/22 下午7:29
 * -------------------------------------
 * 描述：视频模型
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class Video {

    private int width;

    private int height;

    private String cover_pic;

    private String title;

    public Video() {
    }

    public Video(int width, int height, String cover_pic, String title) {
        this.width = width;
        this.height = height;
        this.cover_pic = cover_pic;
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
