package com.tinyurl.app.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LongUrlRequest {
    private String originalUrl;
    private Integer count;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
