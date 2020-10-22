package com.zoro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhang.wenhan
 * @description RedisController
 * @date 2019/9/4 13:52
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/set")
    public String set(@RequestParam("key") String key, @RequestParam("value") String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
    @GetMapping("/get")
    public String get(@RequestParam("key") String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

}
