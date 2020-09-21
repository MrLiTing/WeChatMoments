package com.lee.wechat.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/8 21:02
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: 朋友圈图片Bean
 */

public class ImagesBean implements Parcelable {

    /**
     * 图片地址
     */
    private String url;

    public ImagesBean(String url) {
        this.url = url;
    }

    protected ImagesBean(Parcel in) {
        url = in.readString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
    }

    public static final Creator<ImagesBean> CREATOR = new Creator<ImagesBean>() {
        @Override
        public ImagesBean createFromParcel(Parcel in) {
            return new ImagesBean(in);
        }

        @Override
        public ImagesBean[] newArray(int size) {
            return new ImagesBean[size];
        }
    };

    @Override
    public String toString() {
        return "ImagesBean{" +
                "url='" + url + '\'' +
                '}';
    }
}
