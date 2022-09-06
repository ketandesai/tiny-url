package com.tinyurl.app.model;

import java.util.Date;

public class LongUrlRequest {

    private String hashKey;
    private String originalUrl;
    private Integer count;
    private Date startDate;
    private Date endDate;

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getOriginalUrl(){
        return originalUrl;
    }

    public void setOriginalUrl(String url){
        this.originalUrl = url;
    }

    public Integer getCount(){
        return count;
    }

    public void setCount(Integer count){
        this.count = count;
    }

    public void setStartDate(Date date){
        this.startDate = date;
    }

    public Date getStartDate(){
        return this.startDate;
    }

    public void setEndDate(Date date){
        this.endDate = date;
    }

    public Date getEndDate(){
        return this.endDate;
    }


}
