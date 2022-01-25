package com.thesis.backend.service.interfaces;

import javax.transaction.Transactional;
import java.util.Optional;

public interface EntityService<Entity, Dto, PK>{
    Entity findOneByPrimaryKey(PK primaryKey);
    @Transactional
    Entity saveOne(Dto dto);
}
