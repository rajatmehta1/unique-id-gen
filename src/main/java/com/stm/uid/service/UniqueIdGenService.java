package com.stm.uid.service;

import com.stm.uid.util.IDType;
import com.stm.uid.util.SnowflakeIDGenerator;
import com.stm.uid.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UniqueIdGenService {

    private SnowflakeIDGenerator snowflakeIDGenerator;

    @Autowired
    public UniqueIdGenService(SnowflakeIDGenerator snowflakeIDGenerator) {
        this.snowflakeIDGenerator = snowflakeIDGenerator;
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
                result = new Result(snowflakeIDGenerator.fetchNextId().toString(),IDType.ID_SNOWFLAKE.name());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return result;
    }
}
