package com.sunspot.rich;


/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/4/28 下午5:51
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class KilaSharePlatform {

    private int platform;
    private int titleRes;
    private int displayImg;

    public KilaSharePlatform(int platform, int titleRes, int displayImg) {
        this.platform = platform;
        this.titleRes = titleRes;
        this.displayImg = displayImg;
    }

    public int getPlatform() {
        return platform;
    }

    public int getTitle() {
        return titleRes;
    }

    public int getDisplayImg() {
        return displayImg;
    }

}
