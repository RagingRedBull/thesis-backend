package com.thesis.backend.model.util.mapper;

public interface EntityMapper<Entity, Dto>{
    Dto mapToDto(Entity entity);
    Entity mapToEntity(Dto dto);
}
