package com.thesis.backend.service;

import javax.transaction.Transactional;
import java.util.Optional;

public interface EntityService<Entity, Dto>{
    Optional<Entity> findOneByPrimaryKey(Dto dto);
    @Transactional
    Entity saveOne(Dto dto);
}
