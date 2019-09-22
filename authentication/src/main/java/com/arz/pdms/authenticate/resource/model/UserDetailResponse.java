package com.arz.pdms.authenticate.resource.model;

public class UserDetailResponse {

    private Integer statusCode;
    private String statusDetails;

    public UserDetailResponse(Integer statusCode, String statusDetails) {
        this.statusCode = statusCode;
        this.statusDetails = statusDetails;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(String statusDetails) {
        this.statusDetails = statusDetails;
    }
}
