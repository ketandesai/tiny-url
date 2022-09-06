package com.tinyurl.app.service;

import com.tinyurl.app.controller.UrlController;
import com.tinyurl.app.converter.BytesToUserConverter;
import com.tinyurl.app.converter.UserToBytesConverter;
import com.tinyurl.app.model.User;
import com.tinyurl.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private RedisTemplate redisTemplate;
    private UserRepository userRepository;

    UserService(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.userRepository = new UserRepository(redisTemplate);
    }

    @Autowired
    private UserToBytesConverter userToBytesConverter;

    @Autowired
    private BytesToUserConverter bytesToUserConverter;

    public void saveUser(User user) {
        logger.debug("Using connection factory: " + redisTemplate.getConnectionFactory());
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("user", user.getPhoneNumber(), userToBytesConverter.convert(user));
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return bytesToUserConverter.convert((byte[]) hashOperations.get("user", phoneNumber));
    }

    public void saveUser(){
        userRepository.create(new User("1", "username", "emailid"));
    }

    public User getUser(){
        User user = userRepository.get("1");
        return user;
        //userRepository.update(user);
        //userRepository.delete(user.getUserId());
    }

}