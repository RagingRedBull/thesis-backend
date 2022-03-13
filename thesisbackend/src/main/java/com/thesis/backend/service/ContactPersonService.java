package com.thesis.backend.service;

import com.thesis.backend.model.dto.ContactPersonDto;
import com.thesis.backend.model.entity.ContactPerson;
import com.thesis.backend.repository.ContactPersonRepository;
import com.thesis.backend.service.interfaces.EntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactPersonService implements EntityService<ContactPerson, ContactPersonDto, Integer> {
    private final ContactPersonRepository contactPersonRepository;
    @Override
    public ContactPerson findOneByPrimaryKey(Integer primaryKey) throws EntityNotFoundException {
        return null;
    }

    @Override
    public ContactPerson saveOne(ContactPersonDto contactPersonDto) {
        return null;
    }

    @Override
    public void deleteOne(Integer primaryKey) {

    }

    @Override
    public ContactPerson updateOne(ContactPersonDto contactPersonDto) {
        return null;
    }

    public List<ContactPerson> getAllContactPersons() {
        return contactPersonRepository.findAll();
    }
}
