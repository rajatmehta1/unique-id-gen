package com.stm.uid.task;

import com.stm.uid.domain.IDList;
import com.stm.uid.repository.IDRegistryCacheRepo;
import com.stm.uid.service.UniqueIdGenService;
import com.stm.uid.util.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

// This task will load the ids in the cache
// 100 at a time
@Component
public class LoadCacheTask {

    private IDRegistryCacheRepo idRegistryCacheRepo;
    private StringRedisTemplate stringRedisTemplate;
    private DefaultRedisList<String> defaultRedisList;
    private UniqueIdGenService uniqueIdGenService;

    @Autowired
    public LoadCacheTask(IDRegistryCacheRepo idRegistryCacheRepo,
                         StringRedisTemplate stringRedisTemplate,
                         UniqueIdGenService uniqueIdGenService) {
        this.idRegistryCacheRepo = idRegistryCacheRepo;
        this.stringRedisTemplate = stringRedisTemplate;
        this.uniqueIdGenService = uniqueIdGenService;
    }

    @PostConstruct
    public void init() {
        defaultRedisList = new DefaultRedisList<String>(CacheUtils.getIDListKey(), stringRedisTemplate);
        if(!stringRedisTemplate.hasKey("unique_ids")) {
            stringRedisTemplate.opsForList().leftPushAll("unique_ids", uniqueIdGenService.getIDs());
        }
    }

    @Scheduled(fixedRate = 10000)
    public void loadAdditionalIds() {
        if( 100 > stringRedisTemplate.opsForList().size("unique_ids").intValue()) {
            int r = 100 - stringRedisTemplate.opsForList().size("unique_ids").intValue();

            for(int i = 0 ; i < r ; i++) {
                stringRedisTemplate.opsForList().leftPush("unique_ids",uniqueIdGenService.getSnowflakeId());
            }
        }
    }

    public void loadCache() {
        ArrayList<String> strs = new ArrayList<>();
            strs.add("hellow 3");
            strs.add("asdf");
        IDList idList = new IDList();
            idList.setListID(12345);
            idList.setIds(strs);
        idRegistryCacheRepo.save(idList);
    }



}
