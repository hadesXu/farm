package com.hades.farm.web.model.view.user;

/**
 * 返回给客户端的用户模型
 * Created by Ben on 16/9/5.
 */
public class UserModel {

    /**
     * 用户id
     */
    private long userId;
    /**
     * 用户token
     */
    private String token;
    /**
     * 用户昵称
     */
    private String nick;
    /**
     * 用户头像
     */
    private String face;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 客户端IP地址
     */
    private String ip;
    /**
     * IM账号
     */
    private String chatId;
    /**
     * IM密码
     */
    private String chatPwd;
    /**
     * 数据版本号
     */
    private int syncVersion;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatPwd() {
        return chatPwd;
    }

    public void setChatPwd(String chatPwd) {
        this.chatPwd = chatPwd;
    }

    public int getSyncVersion() {
        return syncVersion;
    }

    public void setSyncVersion(int syncVersion) {
        this.syncVersion = syncVersion;
    }
}
