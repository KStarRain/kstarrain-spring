package com.kstarrain.vo;

/**
 * @author: Dong Yu
 * @create: 2018-11-22 15:50
 * @description:
 */
public class LogWithIp {

    private String ip;
    private String userAgent;
    private String platform;
    private LogContent content;

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public LogContent getContent() {
        return this.content;
    }

    public void setContent(LogContent content) {
        this.content = content;
    }

}
