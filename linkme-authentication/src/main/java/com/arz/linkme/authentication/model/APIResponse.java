package com.arz.linkme.authentication.model;

import java.util.Date;

public class APIResponse {
    private Date timestamp;
    private APIResponseStatus status;
    private Object detail;
    private String path;

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public APIResponse(Date timestamp, APIResponseStatus status, Object detail) {
        this.timestamp = timestamp;
        this.status = status;
        this.detail = detail;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public APIResponseStatus getStatus() {
        return status;
    }

    public void setStatus(APIResponseStatus status) {
        this.status = status;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
