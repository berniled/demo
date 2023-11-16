package com.mynt.services.delivery.demo.model;

import java.util.Date;

public class VoucherItem {
    private String voucherCode;
    private double discount;
    private Date expirDate;
    private String apiKey;
    private String path;

    
    public String getVoucherCode() {
        return voucherCode;
    }
    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public Date getExpirDate() {
        return expirDate;
    }
    public void setExpirDate(Date expirDate) {
        this.expirDate = expirDate;
    }
    public String getApiKey() {
        return apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }


}
