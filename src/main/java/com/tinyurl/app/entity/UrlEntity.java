package com.tinyurl.app.entity;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import lombok.Builder;

@Data
@Builder
@RedisHash
public class UrlEntity{
    private static final long serialVersionUID = 7156526077883281623L;

    @Id
    private Long id;

    private String originalUrl;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer count;
}
