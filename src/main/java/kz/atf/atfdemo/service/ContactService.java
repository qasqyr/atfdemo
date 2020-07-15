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

    public Contact getContactById(Long contactId) throws Exception {
        return contactRepository.findById(contactId).get();
    }

    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact deleteContactById(Long contactId) throws Exception {
        Contact contact = contactRepository.findById(contactId).get();
        contact.setDeleted(true);
        contactRepository.save(contact);
        return contact;
    }
}
