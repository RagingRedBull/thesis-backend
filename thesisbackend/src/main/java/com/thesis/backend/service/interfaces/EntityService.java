package com.thesis.backend.service.interfaces;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

public interface EntityService<Entity, Dto, PK>{
    Entity findOneByPrimaryKey(PK primaryKey) throws EntityNotFoundException;
    @Transactional
    Entity saveOne(Dto dto);
    @Transactional
    void deleteOne(PK primaryKey);
    @Transactional
    Entity updateOne(Dto dto);
}
