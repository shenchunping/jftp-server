package net.kuper.jftp.server.entity;

import java.io.Serializable;

public class FtpUser implements Serializable {
    private String userid;

    private String userpassword;

    private String homedirectory;

    private Boolean enableflag;

    private Boolean writepermission;

    private Integer idletime;

    private Integer uploadrate;

    private Integer downloadrate;

    private Integer maxloginnumber;

    private Integer maxloginperip;

    private static final long serialVersionUID = 1L;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword == null ? null : userpassword.trim();
    }

    public String getHomedirectory() {
        return homedirectory;
    }

    public void setHomedirectory(String homedirectory) {
        this.homedirectory = homedirectory == null ? null : homedirectory.trim();
    }

    public Boolean getEnableflag() {
        return enableflag;
    }

    public void setEnableflag(Boolean enableflag) {
        this.enableflag = enableflag;
    }

    public Boolean getWritepermission() {
        return writepermission;
    }

    public void setWritepermission(Boolean writepermission) {
        this.writepermission = writepermission;
    }

    public Integer getIdletime() {
        return idletime;
    }

    public void setIdletime(Integer idletime) {
        this.idletime = idletime;
    }

    public Integer getUploadrate() {
        return uploadrate;
    }

    public void setUploadrate(Integer uploadrate) {
        this.uploadrate = uploadrate;
    }

    public Integer getDownloadrate() {
        return downloadrate;
    }

    public void setDownloadrate(Integer downloadrate) {
        this.downloadrate = downloadrate;
    }

    public Integer getMaxloginnumber() {
        return maxloginnumber;
    }

    public void setMaxloginnumber(Integer maxloginnumber) {
        this.maxloginnumber = maxloginnumber;
    }

    public Integer getMaxloginperip() {
        return maxloginperip;
    }

    public void setMaxloginperip(Integer maxloginperip) {
        this.maxloginperip = maxloginperip;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", userpassword=").append(userpassword);
        sb.append(", homedirectory=").append(homedirectory);
        sb.append(", enableflag=").append(enableflag);
        sb.append(", writepermission=").append(writepermission);
        sb.append(", idletime=").append(idletime);
        sb.append(", uploadrate=").append(uploadrate);
        sb.append(", downloadrate=").append(downloadrate);
        sb.append(", maxloginnumber=").append(maxloginnumber);
        sb.append(", maxloginperip=").append(maxloginperip);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}