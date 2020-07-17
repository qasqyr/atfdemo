package kz.atf.atfdemo.service;

import kz.atf.atfdemo.model.Contact;
import kz.atf.atfdemo.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    public List<Contact> getAllContactsByUserIdAndDeleted(Long userId, Boolean deleted) {
        return contactRepository.findAllByUserIdAndDeleted(userId, deleted);
    }

    public Contact getContactById(Long contactId) throws NoSuchElementException {
        return contactRepository.findById(contactId).get();
    }

    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact deleteContactById(Long contactId) throws NoSuchElementException {
        Contact contact = contactRepository.findById(contactId).get();
        contact.setDeleted(true);
        contactRepository.save(contact);
        return contact;
    }

    public Long countAllContacts() {
        return contactRepository.count();
    }
}
