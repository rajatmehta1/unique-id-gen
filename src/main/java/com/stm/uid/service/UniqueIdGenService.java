package com.stm.uid.service;

import com.stm.uid.util.IDType;
import com.stm.uid.util.SnowflakeIDGenerator;
import com.stm.uid.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UniqueIdGenService {

    private SnowflakeIDGenerator snowflakeIDGenerator;

    private CacheRegistryService cacheRegistryService;

    @Autowired
    public UniqueIdGenService(SnowflakeIDGenerator snowflakeIDGenerator,
                              CacheRegistryService cacheRegistryService) {
        this.snowflakeIDGenerator = snowflakeIDGenerator;
        this.cacheRegistryService = cacheRegistryService;
    }

    // 128 bit id with chars and non numeric
    public String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public String getSnowflakeId() {
        return snowflakeIDGenerator.fetchNextId().toString();
    }

    public Result getID(String type) {
        IDType idType = IDType.valueOf(type);
        Result result = null;
        switch (idType) {
            case ID_UUID:
                result = new Result(getUUID(), IDType.ID_UUID.name());
                break;
            case ID_SNOWFLAKE:
                String unqId = cacheRegistryService.getID();
                if(null != unqId) {
                    result = new Result(unqId, IDType.ID_SNOWFLAKE.name());
                } else {
                    result = new Result(snowflakeIDGenerator.fetchNextId().toString(), IDType.ID_SNOWFLAKE.name());
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return result;
    }

    public ArrayList<String> getIDs() {
        ArrayList<String> ids = new ArrayList<>();
        for(int i = 0 ; i < 100 ; i++) {
            ids.add(snowflakeIDGenerator.fetchNextId().toString());
        }
        return ids;
    }
}
