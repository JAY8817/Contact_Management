package com.codewithjay.scm0_2.controller;


import com.codewithjay.scm0_2.Service.ContactService;
import com.codewithjay.scm0_2.entities.Contact;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    ContactService contactService;

    @GetMapping("/contacts/{id}")
    public Contact getContact(@PathVariable int id) {
        return contactService.getContactById(id);


    }

}
