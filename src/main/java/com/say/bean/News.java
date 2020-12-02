package com.say.bean;

import java.util.Date;

public class News {
    private Integer id;

    private Integer meid;

    private Integer fid;

    private String content;

    private Date sendingtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMeid() {
        return meid;
    }

    public void setMeid(Integer meid) {
        this.meid = meid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getSendingtime() {
        return sendingtime;
    }

    public void setSendingtime(Date sendingtime) {
        this.sendingtime = sendingtime;
    }
}