package com.tinyurl.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tinyurl.app.model.LongUrlRequest;
import com.tinyurl.app.service.UrlServiceIF;

@RestController
@Slf4j
public class UrlController {
    UrlServiceIF urlService;
    public UrlController(UrlServiceIF urlService){
        this.urlService = urlService;
    }

    /**
     * Converts a long url to a tiny url
     * @param request url object
     * @return
     */
    @PostMapping("/convert")
    public String convertToTinyUrl(@RequestBody LongUrlRequest request) {
        log.debug("coverting long url {}", request.toString());
        return urlService.convertToTinyUrl(request);
    }

    /**
     * Gets the tiny url and redirects to the original (long) url
     * @param tinyUrl
     * @return
     */
    @GetMapping(value = "{tinyUrl}")
    public ResponseEntity<Void> getAndRedirect(@PathVariable String tinyUrl){
        return urlService.getOriginalUrl(tinyUrl);
    }
}
