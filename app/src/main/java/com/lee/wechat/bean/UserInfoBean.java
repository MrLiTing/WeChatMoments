package com.lee.wechat.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/8 20:31
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: 用戶个人信息Bean
 */

public class UserInfoBean implements Parcelable {

    /**
     * 个人资料图片
     */
    private String profileimage;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 用户名
     */
    private String username;

    protected UserInfoBean(Parcel in) {
        profileimage = in.readString();
        avatar = in.readString();
        nick = in.readString();
        username = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profileimage);
        dest.writeString(avatar);
        dest.writeString(nick);
        dest.writeString(username);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel in) {
            return new UserInfoBean(in);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "profileimage='" + profileimage + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nick='" + nick + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
