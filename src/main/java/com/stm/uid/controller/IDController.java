package com.stm.uid.controller;

import com.stm.uid.domain.IDList;
import com.stm.uid.repository.IDRegistryCacheRepo;
import com.stm.uid.service.UniqueIdGenService;
import com.stm.uid.task.LoadCacheTask;
import com.stm.uid.util.IDType;
import com.stm.uid.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/stm")
public class IDController {

    private UniqueIdGenService uniqueIdGenService;

    @Autowired
    private IDRegistryCacheRepo idRegistryCacheRepo;

    @Autowired
    private LoadCacheTask loadCacheTask;

    @Autowired
    public IDController(UniqueIdGenService uniqueIdGenService) {
        this.uniqueIdGenService = uniqueIdGenService;
    }

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String getHello() {
        return "hello from id controller";
    }

    @RequestMapping(path = "/uuid", method = RequestMethod.GET)
    public String getUuid() {
        return uniqueIdGenService.getUUID();
    }

    @RequestMapping(path = "/snowflakeid", method = RequestMethod.GET)
    public String getSnowflakeId() {
        return uniqueIdGenService.getSnowflakeId();
    }

    @RequestMapping(path = "/id/{id_type}", method = RequestMethod.GET)
    public @ResponseBody Result getID(@PathVariable(name = "id_type") String idType) {
        if(null == idType) {
            return uniqueIdGenService.getID(IDType.ID_UUID.toString());
        } else {
            return uniqueIdGenService.getID("ID_" + idType.toUpperCase());
        }
    }


    @GetMapping("/load/test")
    public void saveSampleInCache() {
        loadCacheTask.loadCache();
    }

    @GetMapping("/test/cache")
    public @ResponseBody IDList getIDsSample() {
        return idRegistryCacheRepo.findById("12345").get();
    }

}
