package com.dashboard.saas.controllers.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/redis")
public class RedisController {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @GetMapping("/test")
    public String testRedis() {
        redisTemplate.opsForValue().set("name", "Redis Connected Successfully", 15L, TimeUnit.SECONDS);
        return redisTemplate.opsForValue().get("name");
    }


}
