package com.thesis.backend.model.util.mapper;

public interface Mapper <E, D>{
    public D mapToDto(E entity);
    public E mapToEntity(D dto);
}
