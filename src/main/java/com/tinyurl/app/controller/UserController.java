package com.tinyurl.app.controller;

import com.tinyurl.app.model.User;
import com.tinyurl.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/{phoneNumber}", method = GET, produces = APPLICATION_JSON_VALUE)
    public User findUserByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        userService.saveUser(user);
        return userService.findUserByPhoneNumber(phoneNumber);
    }

    @RequestMapping(value = "/user", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity(CREATED);
    }

    @GetMapping("/adduser")
    public ResponseEntity<?> saveToRedis(){
        logger.debug("adding user to redis ... ");
        userService.saveUser();
        return ResponseEntity.ok("User Added");
    }

    @GetMapping("/getuser")
    public ResponseEntity<?> getFromRedis(){
        logger.debug("getting user from redis ... ");
        User user = userService.getUser();
        if (user == null){
            logger.debug("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        logger.debug(user.toString());
        return ResponseEntity.status(HttpStatus.FOUND).body(user.toString());
    }
}
