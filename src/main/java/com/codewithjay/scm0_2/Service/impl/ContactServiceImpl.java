package com.codewithjay.scm0_2.Service.impl;

import com.codewithjay.scm0_2.Repo.ContactRepo;
import com.codewithjay.scm0_2.Service.ContactService;
import com.codewithjay.scm0_2.entities.Contact;
import com.codewithjay.scm0_2.entities.User;
import com.codewithjay.scm0_2.exception.ResouecenotfoundException;
import com.codewithjay.scm0_2.helper.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepo contactRepo;


    @Override
    public Contact save(Contact contact) {
        return contactRepo.save(contact);
    }

    @Override
    public Contact getContactById(int id) {
        return contactRepo.findById(id).orElseThrow(()-> new ResouecenotfoundException("contact id" ,"id" ,id));
    }


    @Override
    public List<Contact> getAllContacts() {
        return List.of();
    }

    @Override
    public void deleteContact(int id) {
        contactRepo.deleteById(id);

    }

    @Override
    public Contact updateContact(Contact contact) {

        Contact contact1 = contactRepo.findById(contact.getId()).orElseThrow(() -> new ResouecenotfoundException("contact id", "id", contact.getId()));
       contact1.setName(contact.getName());
       contact1.setEmail(contact.getEmail());
       contact1.setAddress(contact.getAddress());
       contact1.setContactImage(contact.getContactImage());
       contact1.setFavorite(contact.getFavorite());
       contact1.setDescription(contact.getDescription());
       contact1.setPhoneNumber(contact.getPhoneNumber());
       contact1.setLinkedInLink(contact.getLinkedInLink());
       contact1.setWebsiteLink(contact.getWebsiteLink());
        return contactRepo.save(contact1);

    }

    @Override
    public List<Contact> getContactsByUserId(int userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getContactsByUser(User user,int page , int size, String sortBy , String order) {

        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(page, AppConstants.Contact_Size, sort);
        return contactRepo.findByUser(user,pageRequest);
    }




    @Override
    public List<Contact> searchAll(User user,String key1 , String key2 , String Key3) {
        return contactRepo.findByUserAndNameContainingOrEmailContainingOrPhoneNumberContaining(user,key1,key2,Key3);
    }




}

