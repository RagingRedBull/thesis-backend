package com.thesis.backend.repository;

import com.thesis.backend.model.entity.ContactPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactPersonRepository extends JpaRepository<ContactPerson, Integer> {
    @Query("SELECT cp FROM ContactPerson cp WHERE cp.delete=false")
    Page<ContactPerson> getAllEnabled(Pageable pageable);
    @Query("UPDATE ContactPerson cp SET cp.delete=true WHERE cp.id=:id")
    @Modifying
    void softDelete(@Param("id") int id);
}
