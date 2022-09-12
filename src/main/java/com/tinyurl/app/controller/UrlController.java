package com.tinyurl.app.controller;

import com.tinyurl.app.model.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tinyurl.app.model.UrlRequest;
import com.tinyurl.app.service.UrlServiceIF;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;

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
    @PostMapping("/tiny/convert")
    public ResponseEntity<?> convertToTinyUrl(@RequestBody UrlRequest request) {
        if (request.getStartDate() == null){
            request.setStartDate(LocalDateTime.now());
        }
        if (request.getEndDate() == null){
            request.setEndDate(LocalDateTime.now().plusHours(1));
        }

        ValidationError validation = new ValidationError();
        if (isValidUrl(request.getOriginalUrl()) ) {
            log.debug("converting long url {}", request.toString());
        } else {
           validation.getErrors().add("Invalid URL");
        }

        //if the end date is before the start date
        if (request.getEndDate().isBefore(request.getStartDate())){
            validation.getErrors().add("Start Date must be before End Date.");
        }

        //if there were validation errors
        if (validation.hasErrors()) {
            validation.setErrorMessage("Validation Failed.  " + validation.getErrors().size() + " error(s)");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(validation);
        }
        return urlService.convertToTinyUrl(request);
    }

    /**
     * Gets the tiny url and redirects to the original (long) url
     * @param tinyUrl
     * @return
     */
    @GetMapping(value = "{tinyUrl}")
    public ResponseEntity<?> getAndRedirect(@PathVariable("tinyUrl") String tinyUrl){
        return urlService.getOriginalUrl(tinyUrl);
    }

    @GetMapping("/tiny/urls")
    public ResponseEntity<?> findAll(){
        return urlService.findAll();
    }

    @DeleteMapping("/tiny/urls")
    public ResponseEntity<?> deleteAll(){
        return urlService.deleteAll();
    }

    @DeleteMapping("/tiny/url/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id){
        return urlService.deleteById(id);
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
