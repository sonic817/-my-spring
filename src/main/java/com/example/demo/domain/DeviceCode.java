package com.example.demo.domain;

public class DeviceCode {

    private Integer	deviceCodeNo;    // '임시 회원번호'
    private Integer	userCodeNo;  // '회원 번호. 어떤 회원이 어떤 단말기 번호로 접속했는지 기록한다.'
    private String	deviceCode;  // '단말기 식별 번호'
    private String	insertTs;
    private String	updateTs;

    public Integer getDeviceCodeNo() {
        return deviceCodeNo;
    }
    public void setDeviceCodeNo(Integer deviceCodeNo) {
        this.deviceCodeNo = deviceCodeNo;
    }


    public Integer getUserCodeNo() {
        return userCodeNo;
    }
    public void setUserCodeNo(Integer userCodeNo) {
        this.userCodeNo = userCodeNo;
    }


    public String getDeviceCode() {
        return deviceCode;
    }
    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }


    public String getInsertTs() {
        return insertTs;
    }
    public void setInsertTs(String insertTs) {
        this.insertTs = insertTs;
    }


    public String getUpdateTs() {
        return updateTs;
    }
    public void setUpdateTs(String updateTs) {
        this.updateTs = updateTs;
    }
}