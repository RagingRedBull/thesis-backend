package com.thesis.backend.controller;


import com.thesis.backend.model.dto.ContactPersonDto;
import com.thesis.backend.model.entity.ContactPerson;
import com.thesis.backend.model.util.mapper.ContactPersonMapper;
import com.thesis.backend.model.util.mapper.EntityMapper;
import com.thesis.backend.service.ContactPersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/contact")
public class ContactPersonController {
    private final ContactPersonService contactPersonService;

    @GetMapping(path = "/all")
    public ResponseEntity<Object> getContacts(@RequestParam int pageSize, @RequestParam int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok(contactPersonService.getAllContactPersons(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getContact(@PathVariable int id) {
        EntityMapper<ContactPerson, ContactPersonDto> mapper = new ContactPersonMapper();
        ContactPersonDto dto = mapper.mapToDto(contactPersonService.findOneByPrimaryKey(id));
        return ResponseEntity.ok(dto);
    }

    @PostMapping(path = "/new")
    public ResponseEntity<Object> newContact(@RequestBody ContactPersonDto contactPersonDto) {
        EntityMapper<ContactPerson, ContactPersonDto> mapper = new ContactPersonMapper();
        contactPersonDto = mapper.mapToDto(contactPersonService.saveOne(contactPersonDto));
        return ResponseEntity.ok(contactPersonDto);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Object> updateContact(@RequestBody ContactPersonDto contactPersonDto) {
        EntityMapper<ContactPerson, ContactPersonDto> mapper = new ContactPersonMapper();
        contactPersonDto = mapper.mapToDto(contactPersonService.updateOne(contactPersonDto));
        return ResponseEntity.ok(contactPersonDto);
    }

    @DeleteMapping(path = "/delete/{cpId}")
    public ResponseEntity<Object> deleteContact(@PathVariable int cpId) {
        int update = contactPersonService.softDelete(cpId);
        return ResponseEntity.ok(update);
    }
}
