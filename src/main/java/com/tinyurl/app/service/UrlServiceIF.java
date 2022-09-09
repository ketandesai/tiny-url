package com.tinyurl.app.service;

import com.tinyurl.app.model.LongUrlRequest;
import org.springframework.http.ResponseEntity;

public interface UrlServiceIF {
    ResponseEntity<?> convertToTinyUrl(LongUrlRequest request);

    ResponseEntity<Void> getOriginalUrl(String tinyUrl);
}
