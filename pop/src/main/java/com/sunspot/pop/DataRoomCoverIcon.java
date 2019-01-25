package com.sunspot.pop;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/1/14 下午4:20
 * -------------------------------------
 * 描述：直播间封面 状态icon
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class DataRoomCoverIcon {

    /**
     * 图标标识：虚拟直播 1，未领红包 2，PK 3，连麦 4，问答 5
     */
    private String name;

    /**
     * 图标地址
     */
    private int iconUrl;

    public DataRoomCoverIcon(String name, int iconUrl) {
        this.name = name;
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(int iconUrl) {
        this.iconUrl = iconUrl;
    }
}
