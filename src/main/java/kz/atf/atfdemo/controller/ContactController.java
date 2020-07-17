package kz.atf.atfdemo.controller;

import kz.atf.atfdemo.dto.ContactDto;
import kz.atf.atfdemo.mapper.ContactMapper;
import kz.atf.atfdemo.model.Contact;
import kz.atf.atfdemo.model.User;
import kz.atf.atfdemo.service.ContactService;
import kz.atf.atfdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;
    private final ContactMapper contactMapper;
    private final UserService userService;

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
            User user = userService.getUserById(contactDto.getUserId());
            Contact contact = contactMapper.toContact(user, contactDto);
            contactService.save(contact);
            return ResponseEntity.ok(contactMapper.toContactDto(contact));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
