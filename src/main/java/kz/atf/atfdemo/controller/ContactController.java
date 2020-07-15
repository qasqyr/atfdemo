package kz.atf.atfdemo.controller;

import kz.atf.atfdemo.dto.ContactDto;
import kz.atf.atfdemo.mapper.ContactMapper;
import kz.atf.atfdemo.model.Contact;
import kz.atf.atfdemo.model.User;
import kz.atf.atfdemo.service.ContactService;
import kz.atf.atfdemo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ContactController {

    private ContactService contactService;
    private ContactMapper contactMapper;
    private UserService userService;

    public ContactController(ContactService contactService, ContactMapper contactMapper, UserService userService) {
        this.contactService = contactService;
        this.contactMapper = contactMapper;
        this.userService = userService;
    }

    @GetMapping("/contacts/{contactId}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable Long contactId) {
        Contact contact;
        try {
            contact = contactService.getContactById(contactId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contactMapper.toContactDto(contact));
    }

    @PostMapping("/contacts")
    public ResponseEntity<ContactDto> saveContact(@RequestBody ContactDto contactDto) {
        try {
            User user = userService.getUserById(contactDto.userId);
            Contact contact = contactMapper.toContact(user, contactDto);
            contactService.save(contact);
            return ResponseEntity.ok(contactMapper.toContactDto(contact));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/contacts/{contactId}")
    public ResponseEntity<ContactDto> deleteContactById(@PathVariable Long contactId) {
        Contact contact;
        try {
            contact = contactService.deleteContactById(contactId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contactMapper.toContactDto(contact));
    }
}
