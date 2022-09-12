package com.tinyurl.app.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlRequest {
    private String originalUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
