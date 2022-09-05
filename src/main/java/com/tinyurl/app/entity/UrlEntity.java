package com.tinyurl.app.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
//import javax.persistence.*;

//@Entity
public class UrlEntity implements Serializable{

    private static final long serialVersionUID = 7156526077883281623L;

    @Id
    //@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_USER", allocationSize = 1)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    private Long id;
    private String originalUrl;
    private Date startDate;
    private Date endDate;

    public String getOriginalUrl(){
        return this.originalUrl;
    }

    public void setOriginalUrl(String url){
        this.originalUrl = url;
    }
    
    public Date getStartDate(){
        return this.startDate;
    }

    public void setStartDate(Date date){
        this.startDate = date;
    }

    public Date getEndDate(){
        return this.endDate;
    }

    public void setEndDate(Date date) {
        this.endDate = date;
    }
    
    @Override
    public String toString() {
        return String.format("User{id=%d, originalUrl='%s', startDate=%tT, endDate=%tT}", id, originalUrl, startDate, endDate);
    }
  
    
}
