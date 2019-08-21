package com.sunspot.sharelement.album;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019/7/3 上午11:56
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class PhotoModel implements Parcelable {

    private int imgRes;

    public PhotoModel(int imgRes) {
        this.imgRes = imgRes;
    }

    protected PhotoModel(Parcel in) {
        imgRes = in.readInt();
    }

    public static final Creator<PhotoModel> CREATOR = new Creator<PhotoModel>() {
        @Override
        public PhotoModel createFromParcel(Parcel in) {
            return new PhotoModel(in);
        }

        @Override
        public PhotoModel[] newArray(int size) {
            return new PhotoModel[size];
        }
    };

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imgRes);
    }
}
