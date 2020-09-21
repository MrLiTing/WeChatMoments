package com.lee.wechat.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/8 20:37
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: 朋友圈推文Bean
 */

public class TweetsBean implements Parcelable {
    /**
     * 朋友圈内容
     */
    private String content;
    /**
     * 朋友圈发送者
     */
    private SenderBean sender;
    /**
     * 错误处理
     */
    private String error;
    /**
     * 朋友圈图片列表
     */
    private List<ImagesBean> images;
    /**
     * 朋友圈评论列表
     */
    private List<CommentsBean> comments;


    @SerializedName("unknown error")
    private String unknownError;

    public TweetsBean(String content) {
        this.content = content;
    }

    protected TweetsBean(Parcel in) {
        content = in.readString();
        sender = in.readParcelable(SenderBean.class.getClassLoader());
        error = in.readString();
        images = in.createTypedArrayList(ImagesBean.CREATOR);
        comments = in.createTypedArrayList(CommentsBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeParcelable(sender, flags);
        dest.writeString(error);
        dest.writeTypedList(images);
        dest.writeTypedList(comments);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TweetsBean> CREATOR = new Creator<TweetsBean>() {
        @Override
        public TweetsBean createFromParcel(Parcel in) {
            return new TweetsBean(in);
        }

        @Override
        public TweetsBean[] newArray(int size) {
            return new TweetsBean[size];
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }


    @Override
    public String toString() {
        return "TweetsBean{" +
                "content='" + content + '\'' +
                ", sender=" + sender +
                ", error='" + error + '\'' +
                ", images=" + images +
                ", comments=" + comments +
                '}';
    }

    public String getUnknownError() {
        return unknownError;
    }

    public void setUnknownError(String unknownError) {
        this.unknownError = unknownError;
    }
}
