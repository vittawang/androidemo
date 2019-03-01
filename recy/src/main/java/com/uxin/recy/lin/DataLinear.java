package com.uxin.recy.lin;

import java.util.List;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/2/20 下午4:30
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class DataLinear {

    private List<DataComment> commentList;

    public DataLinear(List<DataComment> commentList) {
        this.commentList = commentList;
    }

    public List<DataComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<DataComment> commentList) {
        this.commentList = commentList;
    }
}
