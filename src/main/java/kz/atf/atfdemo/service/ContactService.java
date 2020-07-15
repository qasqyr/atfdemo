package kz.atf.atfdemo.service;

import kz.atf.atfdemo.model.Contact;
import kz.atf.atfdemo.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContactsByUserId(Long userId) {
        return contactRepository.findAllByUserId(userId);
    }
}
