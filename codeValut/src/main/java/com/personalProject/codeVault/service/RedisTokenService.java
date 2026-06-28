package com.personalProject.codeVault.service;

import com.personalProject.codeVault.model.Snippet;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisTokenService(RedisTemplate<String,String> redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    //store the token with the expiry
//    public saveToken(String token, Long snippetId){
//        //set(K key, V value, long timeout, TimeUnit unit)--syntax
//        redisTemplate.opsForValue().set("Share_Token"+token,String.valueOf(snippetId),1, TimeUnit.HOURS);
//    }

    //return null id expired or not found



}
