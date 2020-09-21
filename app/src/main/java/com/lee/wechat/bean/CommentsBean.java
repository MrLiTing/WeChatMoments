package com.lee.wechat.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/8 20:53
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: 朋友圈评论Bean
 */

public class CommentsBean implements Parcelable {

    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论发送者
     */
    private SenderBean sender;

    protected CommentsBean(Parcel in) {
        content = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommentsBean> CREATOR = new Creator<CommentsBean>() {
        @Override
        public CommentsBean createFromParcel(Parcel in) {
            return new CommentsBean(in);
        }

        @Override
        public CommentsBean[] newArray(int size) {
            return new CommentsBean[size];
        }
    };

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SenderBean getSender() {
        return sender;
    }

    public void setSender(SenderBean sender) {
        this.sender = sender;
    }


    @Override
    public String toString() {
        return "CommentsBean{" +
                "content='" + content + '\'' +
                ", sender=" + sender +
                '}';
    }
}
