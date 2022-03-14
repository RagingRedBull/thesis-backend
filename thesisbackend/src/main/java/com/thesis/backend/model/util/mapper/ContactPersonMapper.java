package com.thesis.backend.model.util.mapper;

import com.thesis.backend.model.dto.ContactPersonDto;
import com.thesis.backend.model.entity.ContactPerson;

public class ContactPersonMapper implements EntityMapper<ContactPerson, ContactPersonDto> {
    @Override
    public ContactPersonDto mapToDto(ContactPerson contactPerson) {
        ContactPersonDto dto = new ContactPersonDto();
        dto.setFirstName(contactPerson.getFirstName());
        dto.setLastName(contactPerson.getLastName());
        dto.setPhoneNumber(contactPerson.getPhoneNumber());
        dto.setEmail(contactPerson.getEmail());
        dto.setId(contactPerson.getId());
        return dto;
    }

    @Override
    public ContactPerson mapToEntity(ContactPersonDto contactPersonDto) {
        ContactPerson entity = new ContactPerson();
        entity.setFirstName(contactPersonDto.getFirstName());
        entity.setLastName(contactPersonDto.getLastName());
        entity.setPhoneNumber(contactPersonDto.getPhoneNumber());
        entity.setEmail(contactPersonDto.getEmail());
        entity.setId(contactPersonDto.getId());
        return entity;
    }
}
