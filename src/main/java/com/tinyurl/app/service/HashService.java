package com.tinyurl.app.service;

import java.security.NoSuchAlgorithmException;

public interface HashService {
    
    String generateHash(String url) throws NoSuchAlgorithmException;
}
