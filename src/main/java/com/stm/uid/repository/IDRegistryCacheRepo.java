package com.stm.uid.repository;

import com.stm.uid.domain.IDList;
import org.springframework.data.repository.CrudRepository;

public interface IDRegistryCacheRepo extends CrudRepository<IDList, String> {
}
