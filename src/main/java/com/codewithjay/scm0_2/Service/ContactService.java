package com.codewithjay.scm0_2.Service;

import com.codewithjay.scm0_2.entities.Contact;
import com.codewithjay.scm0_2.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactService {


    Contact save(Contact contact);

    Contact getContactById(int id);

    List<Contact> getAllContacts();

    void deleteContact(int id);

    Contact updateContact(Contact contact);

    List<Contact> getContactsByUserId(int UserId);

    Page<Contact> getContactsByUser(User user, int page, int size, String sortBy, String order);

    List<Contact> searchAll(User user, String key1, String key2, String Key3);
}
