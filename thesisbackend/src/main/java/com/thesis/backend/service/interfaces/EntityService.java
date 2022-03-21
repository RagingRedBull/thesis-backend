package com.thesis.backend.service.interfaces;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

public interface EntityService<Entity, Dto, PK>{
    Entity findOneByPrimaryKey(PK primaryKey) throws EntityNotFoundException;
    Entity saveOne(Dto dto);
    void deleteOne(PK primaryKey);
    Entity updateOne(Dto dto);
}
