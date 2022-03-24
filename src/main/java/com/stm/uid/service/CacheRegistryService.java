package com.stm.uid.service;

import com.stm.uid.repository.IDRegistryCacheRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

//Decoupling the Cache operation incase
//we change caching solution later
@Service
public class CacheRegistryService {

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public CacheRegistryService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public String getID() {
        String idValue = this.stringRedisTemplate.opsForList().rightPop("unique_ids"); // pop one item
        return idValue;
    }
}
