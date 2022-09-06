package com.tinyurl.app.controller;

import java.net.URI;
import java.util.AbstractMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tinyurl.app.model.LongUrlRequest;
import com.tinyurl.app.service.UrlServiceIF;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class UrlController {

    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);
    
    UrlServiceIF urlService;

    @Autowired
    private RedisTemplate redisTemplate;

    public UrlController(UrlServiceIF urlService){
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
    /*@GetMapping(value = "{tinyUrl}")
    public ResponseEntity<Void> getAndRedirect(@PathVariable String tinyUrl){
        String longUrl = urlService.getOriginalUrl(tinyUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                             .location(URI.create(longUrl))
                             .build();
    }*/

    @GetMapping("/testredis")
    public String testRedis(){
        logger.debug("testing connection to redis ... ");
        return urlService.testRedis();
    }

    @GetMapping("/strings/{key}")
    public Map.Entry<String, String> getString(@PathVariable("key") String key) {
        String STRING_KEY_PREFIX = "redi2read:strings:";
        String value = (String)redisTemplate.opsForValue().get(STRING_KEY_PREFIX + key);

        if (value == null) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "key not found");
        }
        return new AbstractMap.SimpleEntry<String, String>(key, value);
    }



}
