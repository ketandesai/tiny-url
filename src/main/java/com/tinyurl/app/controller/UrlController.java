package com.tinyurl.app.controller;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tinyurl.app.model.LongUrlRequest;
import com.tinyurl.app.service.UrlService;


@RestController
public class UrlController {

    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);
    
    UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    
    /**
     * Converts a long url to a tiny url
     * @param request url object
     * @return
     */
    @PostMapping("/convert")
    public String convertToTinyUrl(LongUrlRequest request) {
        logger.debug("coverting long url {}", request.getOriginalUrl());
        return urlService.convertToTinyUrl(request);
    }

    
    /**
     * Gets the tiny url and redirects to the original (long) url
     * @param tinyUrl
     * @return
     */
    @GetMapping(value = "{tinyUrl}")
    public ResponseEntity<Void> getAndRedirect(@PathVariable String tinyUrl){
        String longUrl = urlService.getOriginalUrl(tinyUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                             .location(URI.create(longUrl))
                             .build();
    }

}
