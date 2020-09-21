package com.lee.wechat.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/8 20:52
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: 朋友圈发送者Bean
 */

public class SenderBean implements Parcelable {

    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 头像
     */
    private String avatar;

    protected SenderBean(Parcel in) {
        username = in.readString();
        nick = in.readString();
        avatar = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(nick);
        dest.writeString(avatar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SenderBean> CREATOR = new Creator<SenderBean>() {
        @Override
        public SenderBean createFromParcel(Parcel in) {
            return new SenderBean(in);
        }

        @Override
        public SenderBean[] newArray(int size) {
            return new SenderBean[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "SenderBean{" +
                "username='" + username + '\'' +
                ", nick='" + nick + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
