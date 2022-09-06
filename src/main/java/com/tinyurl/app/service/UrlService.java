package com.tinyurl.app.service;

import java.util.Date;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.tinyurl.app.entity.UrlEntity;
import com.tinyurl.app.model.LongUrlRequest;
import com.tinyurl.app.repository.UrlRepository;

@Service
public class UrlService implements UrlServiceIF {
    private RedisTemplate redisTemplate;

    private UrlRepository urlRepository;

    public UrlService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.urlRepository = new UrlRepository(this.redisTemplate);
    }

    
    /**
     * Convert the long (original url) to a short url
     * Saves the url to the database,  the converts the auto-generated
     * id to a base 62 number.
     */
    @Override
    public String convertToTinyUrl(LongUrlRequest request) {
        var urlToSave = new UrlEntity();
        urlToSave.setOriginalUrl(request.getOriginalUrl());
        urlToSave.setEndDate(request.getEndDate());
        urlToSave.setStartDate(new Date());
        //var entity = urlRepository.save(urlToSave);

        //String tinyUrl = Base64Utils.encodeToUrlSafeString(entity.getId());
        //String tinyUrl = conversion.encode(entity.getId());
        //urlToSave.setTinyUrl(tinyUrl);
        //urlRepository.save(tinyUrl);
        //return tinyUrl;
        return null;
    }

    /**
     * 
     */
    @Override
    public String getOriginalUrl(String tinyUrl) {
        /*var id = Base64Utils.decodeFromUrlSafeString(tinyUrl);
        var entity = urlRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entity with " + tinyUrl));

        if (entity.getEndDate() != null && entity.getEndDate().before(new Date())){
            urlRepository.delete(entity);
            throw new EntityNotFoundException("Link expired!");
        }

        return entity.getOriginalUrl();
        */
        return null;
    }

    @Override
    public String testRedis(){
        HashOperations hashOps = redisTemplate.opsForHash();
        hashOps.put("user", "123-456-4334", "value");
        return "connection worked";
    }
}
