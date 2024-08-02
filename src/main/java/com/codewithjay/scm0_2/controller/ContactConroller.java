package com.codewithjay.scm0_2.controller;

import com.codewithjay.scm0_2.Service.ContactService;
import com.codewithjay.scm0_2.Service.ImageServices;
import com.codewithjay.scm0_2.Service.UserService;
import com.codewithjay.scm0_2.entities.Contact;
import com.codewithjay.scm0_2.entities.User;
import com.codewithjay.scm0_2.forms.ContactForm;
import com.codewithjay.scm0_2.helper.Helper;
import com.codewithjay.scm0_2.helper.MessageHelper;
import com.codewithjay.scm0_2.helper.MessageType;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user/contacts")
public class ContactConroller {


    @Autowired
    UserService userService;

    @Autowired
    public ImageServices imageServices;

    @Autowired
    ContactService contactService;

    @Autowired
    public com.codewithjay.scm0_2.helper.sesstionHelper sesstionHelper;


    @GetMapping("/add")
    // add contact page: handler
    public String addContactView(Model model) {
        System.out.println("********* Get request in contact form ********");
        ContactForm contactForm = new ContactForm();

//        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);

        System.out.println(contactForm.toString());
        return "user/add_contact";
    }

    @PostMapping("add")
    public String savecontact(@Valid @Validated ContactForm contactForm, BindingResult result, Authentication authentication, Model model, HttpSession session) throws IOException {
        System.out.println("********* post request in contact form ********");
        if (result.hasErrors()) {
//
            result.getAllErrors().forEach(error -> System.out.println(error.toString()));
//
            session.setAttribute("message", MessageHelper.builder().content("Please correct the following errors").type(MessageType.red).build());
            return "user/add_contact";
        }


        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Contact contact = getContact(contactForm, user);

        contactService.save(contact);

        session.setAttribute("message", MessageHelper.builder().content("your contact added successfully").type(MessageType.green).build());


        return "redirect:/user/contacts/all";

    }

    public Contact getContact(ContactForm contactForm, User user) throws IOException {

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.getFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());

        if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String file = UUID.randomUUID().toString();
            String image = imageServices.uploadImage(contactForm.getContactImage(),file);
            System.out.println("image is:-"+image);
            contact.setContactImage(image);
        }


        return contact;
    }


    @GetMapping("/all")
    public String allContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "1") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "order", defaultValue = "ascending") String order,

            Model model, Authentication authentication) {

        String loginuser = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(loginuser);

        Page<Contact> pagecontact = contactService.getContactsByUser(user, page, size, sortBy, order);


        model.addAttribute("pagecontacts", pagecontact);
        return "user/Contacts";
    }


    @RequestMapping("/search")
    public String search(Model model, Authentication authentication,
                         @RequestParam("keyword") String keyword) {

        String loginuser = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(loginuser);


        List<Contact> contacts = contactService.searchAll(user, keyword, keyword, keyword);

        model.addAttribute("pagecontacts", contacts);

        return "user/search";
    }

    @GetMapping("delete/{id}")
    public String contact_delete(@PathVariable int id, HttpSession session) {

        contactService.deleteContact(id);

        session.setAttribute("message", MessageHelper.builder().content("your contact is deleted successfully").type(MessageType.green).build());

        System.out.println("contact_delete :- " + id);
        return "redirect:/user/contacts/all";

    }

    @GetMapping("/update/{id}")
    public String updateContactView(@PathVariable int id, Model model, ContactForm contactForm, Authentication authentication) {


        System.out.println("contact form:-" + contactForm);
        Contact contact = contactService.getContactById(id);

        contactForm.setName(contact.getName());
        contactForm.setFavorite(contact.getFavorite());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setPicture(contact.getContactImage());


        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contact_id", id);
        return "user/update_contact";

    }

    @PostMapping("/updateProcess/{contact_id}")
    public String updateProcess(@Valid @PathVariable("contact_id") int contact_id, @ModelAttribute ContactForm contactForm, HttpSession session) {

//        if (results.hasErrors()) {
////
//            results.getAllErrors().forEach(error -> System.out.println(error.toString()));
////
//            session.setAttribute("message", MessageHelper.builder().content("Please correct the following errors").type(MessageType.red).build());
//            return "redirect : /user/contacts/update";
//        }


        var con = contactService.getContactById(contact_id);
        con.setId(contact_id);
        con.setName(contactForm.getName());
        con.setFavorite(contactForm.getFavorite());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setLinkedInLink(contactForm.getLinkedInLink());
        con.setWebsiteLink(contactForm.getWebsiteLink());

        if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String file = UUID.randomUUID().toString();
            String image = imageServices.uploadImage(contactForm.getContactImage(),file);
            System.out.println("image is:-"+image);
            con.setContactImage(image);
        }

        Contact update = contactService.updateContact(con);

        return "redirect:/user/contacts/all";
    }


}
