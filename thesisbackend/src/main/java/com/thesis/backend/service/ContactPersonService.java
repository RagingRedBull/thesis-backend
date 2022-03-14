package com.thesis.backend.service;

import com.thesis.backend.exception.PrmtsEntityNotFoundException;
import com.thesis.backend.model.dto.ContactPersonDto;
import com.thesis.backend.model.entity.Compartment;
import com.thesis.backend.model.entity.ContactPerson;
import com.thesis.backend.model.util.mapper.ContactPersonMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.repository.ContactPersonRepository;
import com.thesis.backend.service.interfaces.EntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactPersonService implements EntityService<ContactPerson, ContactPersonDto, Integer> {
    private final ContactPersonRepository contactPersonRepository;

    @Override
    public ContactPerson findOneByPrimaryKey(Integer primaryKey) throws EntityNotFoundException {
        Optional<ContactPerson> entity = contactPersonRepository.findById(primaryKey);
        if (entity.isEmpty()) {
            throw new PrmtsEntityNotFoundException(Compartment.class, primaryKey);
        } else {
            return entity.get();
        }
    }

    @Transactional
    @Override
    public ContactPerson saveOne(ContactPersonDto contactPersonDto) {
        EntityMapper<ContactPerson, ContactPersonDto> mapper = new ContactPersonMapper();
        return contactPersonRepository.saveAndFlush(mapper.mapToEntity(contactPersonDto));
    }

    @Transactional
    @Override
    public void deleteOne(Integer primaryKey) {
        contactPersonRepository.softDelete(primaryKey);
    }

    @Transactional
    public int softDelete(int primaryKey) {
        return contactPersonRepository.softDelete(primaryKey);

    }

    @Transactional
    @Override
    public ContactPerson updateOne(ContactPersonDto contactPersonDto) {
        EntityMapper<ContactPerson, ContactPersonDto> mapper = new ContactPersonMapper();
        return contactPersonRepository.saveAndFlush(mapper.mapToEntity(contactPersonDto));
    }

    public Page<ContactPerson> getAllContactPersons(Pageable pageable) {
        return contactPersonRepository.getAllEnabled(pageable);
    }
}
