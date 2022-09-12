package com.tinyurl.app.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UrlResponse {

    private Long id;
    private String originalUrl;
    private Integer count;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String tinyUrl;

}
