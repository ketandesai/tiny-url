package com.tinyurl.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tinyurl.app.model.LongUrlRequest;
import com.tinyurl.app.service.UrlServiceIF;

import java.net.URI;
import java.net.URL;

@RestController
@Slf4j
public class UrlController {
    UrlServiceIF urlService;
    public UrlController(UrlServiceIF urlService){
        this.urlService = urlService;
    }

    @GetMapping("/")
    public ResponseEntity<Void> redirectToSwagger(){
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                .location(URI.create("/swagger-ui/index.html"))
                .build();
    }

    /**
     * Converts a long url to a tiny url
     * @param request url object
     * @return
     */
    @PostMapping("/convert")
    public ResponseEntity<?> convertToTinyUrl(@RequestBody LongUrlRequest request) {
        if (isValidUrl(request.getOriginalUrl()) ) {
            log.debug("coverting long url {}", request.toString());
        } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Enter a valid url to shorten");
        }

        //if the end date is before the start date
        if (request.getEndDate().isBefore(request.getStartDate())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Start Date must be before End Date.");
        }

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

    private boolean isValidUrl(String url){
        // Try creating a valid URL
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
