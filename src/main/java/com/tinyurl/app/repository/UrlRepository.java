package com.tinyurl.app.repository;

import com.tinyurl.app.model.LongUrlRequest;
import com.tinyurl.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
//import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public class UrlRepository{
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlRepository.class);

    final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    final String KEY = "URL";
    private HashOperations hashOperations;

    public UrlRepository(RedisTemplate redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void create(LongUrlRequest url) {
        hashOperations.put(KEY, url.getHashKey(), url);
        logger.debug(String.format("URL with ID %s saved", url.getHashKey()));
    }

    public User get(String userId) {
        return (User) hashOperations.get("USER", userId);
    }

    public Map<String, User> getAll(){
        return hashOperations.entries("USER");
    }

    public void update(LongUrlRequest url) {
        hashOperations.put(KEY, url.getHashKey(), url);
        logger.debug(String.format("URL with ID %s updated", url.getHashKey()));
    }

    public void delete(String hashKey) {
        hashOperations.delete(KEY, hashKey);
        logger.debug(String.format("URL with ID %s deleted", hashKey));
    }

 /*   private final String idKey;
    private final String urlKey;
    public UrlRepository() {
        this.idKey = "id";
        this.urlKey = "url:";
    }

    public UrlRepository(Jedis jedis, String idKey, String urlKey) {
        this.idKey = idKey;
        this.urlKey = urlKey;
    }

    public Long incrementID() {
        Long id = jedis.incr(idKey);
        LOGGER.info("Incrementing ID: {}", id-1);
        return id - 1;
    }

    public void saveUrl(String key, String longUrl) {
        LOGGER.info("Saving: {} at {}", longUrl, key);
        jedis.hset(urlKey, key, longUrl);
    }

    public String getUrl(Long id) throws Exception {
        LOGGER.info("Retrieving at {}", id);
        String url = jedis.hget(urlKey, "url:"+id);
        LOGGER.info("Retrieved {} at {}", url ,id);
        if (url == null) {
            throw new Exception("URL at key" + id + " does not exist");
        }
        return url;
    }*/
    
}
