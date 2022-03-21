package com.thesis.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactPersonDto {
    private int id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String email;
}
