package com.tinyurl.app.service;

import com.tinyurl.app.model.UrlRequest;
import org.springframework.http.ResponseEntity;

public interface UrlServiceIF {
    ResponseEntity<?> convertToTinyUrl(UrlRequest request);

    ResponseEntity<?> getOriginalUrl(String tinyUrl);

    ResponseEntity<?> findAll();

    ResponseEntity<?> deleteAll();
    ResponseEntity<?> deleteById(String id);
}
