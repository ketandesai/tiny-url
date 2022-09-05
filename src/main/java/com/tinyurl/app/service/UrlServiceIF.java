package com.tinyurl.app.service;

import com.tinyurl.app.model.LongUrlRequest;

public interface UrlServiceIF {
    
    String convertToTinyUrl(LongUrlRequest request);

    String getOriginalUrl(String tinyUrl);

    String testRedis();
}
