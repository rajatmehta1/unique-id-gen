package com.stm.uid.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;

@RedisHash("ids_lst")
@Data
public class IDList {

    @Id
    private Integer listID;

    private ArrayList<String> ids;
}
