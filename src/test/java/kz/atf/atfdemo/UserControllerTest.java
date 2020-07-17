package kz.atf.atfdemo;

import kz.atf.atfdemo.dto.ContactDto;
import kz.atf.atfdemo.dto.UserDto;
import kz.atf.atfdemo.mapper.ContactMapper;
import kz.atf.atfdemo.mapper.UserMapper;
import kz.atf.atfdemo.model.Contact;
import kz.atf.atfdemo.model.User;
import kz.atf.atfdemo.service.ContactService;
import kz.atf.atfdemo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;

public class UserControllerTest extends AbstractTest {
    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ContactMapper contactMapper;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    //Test for creation of user
    public void createUser() throws Exception {
        String uri = "/api/users";
        User requestedUser = new User();
        requestedUser.setFirstName("FirstName");
        requestedUser.setLastName("LastName");
        requestedUser.setPatronymicName("PatronymicName");
        Long expectedId = userService.countAllUsers() + 1;

        String inputJson = super.mapToJson(requestedUser);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        User respondedUser = super.mapFromJson(content, User.class);

        assertEquals(expectedId, respondedUser.getId());
        assertEquals(requestedUser.getFirstName(), respondedUser.getFirstName());
        assertEquals(requestedUser.getLastName(), respondedUser.getLastName());
        assertEquals(requestedUser.getPatronymicName(), respondedUser.getPatronymicName());
        assertEquals(false, respondedUser.getDeleted());
    }

    @Test
    //Test for update of user
    public void updateUser() throws Exception {
        String uri = "/api/users";
        createUser();
        Long userId = userService.countAllUsers();

        User requestedUser = new User();
        requestedUser.setId(userId);
        requestedUser.setFirstName("newFirstName");
        requestedUser.setLastName("newLastName");
        requestedUser.setPatronymicName("newPatronymicName");

        String inputJson = super.mapToJson(requestedUser);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        User respondedUser = super.mapFromJson(content, User.class);

        assertEquals(userId, respondedUser.getId());
        assertEquals(requestedUser.getFirstName(), respondedUser.getFirstName());
        assertEquals(requestedUser.getLastName(), respondedUser.getLastName());
        assertEquals(requestedUser.getPatronymicName(), respondedUser.getPatronymicName());
        assertEquals(false, respondedUser.getDeleted());

    }

    @Test
    //Test for contact creation by user
    public void createContactByUser() throws Exception {
        createUser();
        Long userId = userService.countAllUsers();
        String uri = "/api/contacts";

        //Test for Mobile Contact
        Contact requestedContact = new Contact();
        Long expectedId = contactService.countAllContacts() + 1;
        requestedContact.setUser(userService.getUserById(userId));
        requestedContact.setPhoneNumber("+777777777777");
        requestedContact.setType(Contact.Type.MOBILE);
        ContactDto expectedContactDto = contactMapper.toContactDto(requestedContact);

        String inputJson = super.mapToJson(expectedContactDto);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ContactDto actualContactDto = super.mapFromJson(content, ContactDto.class);

        assertEquals(userId, actualContactDto.getUserId());
        assertEquals(expectedId, actualContactDto.getId());
        assertEquals(expectedContactDto.getPhoneNumber(), actualContactDto.getPhoneNumber());
        assertEquals(expectedContactDto.getType(), actualContactDto.getType());
        assertEquals(false, actualContactDto.getDeleted());

        //Test for Home Contact
        requestedContact = new Contact();
        expectedId = contactService.countAllContacts() + 1;
        requestedContact.setUser(userService.getUserById(userId));
        requestedContact.setPhoneNumber("+2991234");
        requestedContact.setType(Contact.Type.HOME);
        expectedContactDto = contactMapper.toContactDto(requestedContact);

        inputJson = super.mapToJson(expectedContactDto);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        actualContactDto = super.mapFromJson(content, ContactDto.class);

        assertEquals(userId, actualContactDto.getUserId());
        assertEquals(expectedId, actualContactDto.getId());
        assertEquals(expectedContactDto.getPhoneNumber(), actualContactDto.getPhoneNumber());
        assertEquals(expectedContactDto.getType(), actualContactDto.getType());
        assertEquals(false, actualContactDto.getDeleted());

        //Test for Work Contact
        requestedContact = new Contact();
        expectedId = contactService.countAllContacts() + 1;
        requestedContact.setUser(userService.getUserById(userId));
        requestedContact.setPhoneNumber("+3159985");
        requestedContact.setType(Contact.Type.WORK);
        expectedContactDto = contactMapper.toContactDto(requestedContact);

        inputJson = super.mapToJson(expectedContactDto);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        actualContactDto = super.mapFromJson(content, ContactDto.class);

        assertEquals(userId, actualContactDto.getUserId());
        assertEquals(expectedId, actualContactDto.getId());
        assertEquals(expectedContactDto.getPhoneNumber(), actualContactDto.getPhoneNumber());
        assertEquals(expectedContactDto.getType(), actualContactDto.getType());
        assertEquals(false, actualContactDto.getDeleted());
    }

    @Test
    //Test for getting info about user and his contacts
    public void getUserAndContacts() throws Exception {
        createContactByUser();
        Long contactId = contactService.countAllContacts();
        Contact contact = contactService.getContactById(contactId);
        User user = userService.getUserById(contact.getUser().getId());
        String uri = "/api/users/" + user.getId();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        UserDto actualUserDto = super.mapFromJson(content, UserDto.class);
        UserDto expectedUserDto = userMapper.toUserDto(user);

        assertEquals(expectedUserDto.getId(), actualUserDto.getId());
        assertEquals(expectedUserDto.getContacts(), actualUserDto.getContacts());
        assertEquals(expectedUserDto.getFirstName(), actualUserDto.getFirstName());
        assertEquals(expectedUserDto.getLastName(), actualUserDto.getLastName());
        assertEquals(expectedUserDto.getPatronymicName(), actualUserDto.getPatronymicName());
        assertEquals(expectedUserDto.getDeleted(), actualUserDto.getDeleted());

    }

    @Test
    //Test for contact update by user
    public void updateContactByUser() throws Exception {
        String uri = "/api/contacts";
        createContactByUser();
        Long contactId = contactService.countAllContacts();
        Contact contact = contactService.getContactById(contactId);
        //Changing Type of Contact to Mobile
        contact.setType(Contact.Type.MOBILE);
        ContactDto expectedContactDto = contactMapper.toContactDto(contact);

        String inputJson = super.mapToJson(expectedContactDto);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ContactDto actualContactDto = super.mapFromJson(content, ContactDto.class);

        assertEquals(contactId, actualContactDto.getId());
        assertEquals(expectedContactDto.getPhoneNumber(), actualContactDto.getPhoneNumber());
        assertEquals(expectedContactDto.getType(), actualContactDto.getType());
        assertEquals(false, actualContactDto.getDeleted());

        //Changing Type of Contact to Home
        contact.setType(Contact.Type.HOME);
        expectedContactDto = contactMapper.toContactDto(contact);

        inputJson = super.mapToJson(expectedContactDto);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        content = mvcResult.getResponse().getContentAsString();
        actualContactDto = super.mapFromJson(content, ContactDto.class);

        assertEquals(contactId, actualContactDto.getId());
        assertEquals(expectedContactDto.getPhoneNumber(), actualContactDto.getPhoneNumber());
        assertEquals(expectedContactDto.getType(), actualContactDto.getType());
        assertEquals(false, actualContactDto.getDeleted());
    }

    @Test
    //Test for user deletion
    public void deleteUser() throws Exception {
        createUser();
        Long userId = userService.countAllUsers();
        String uri = "/api/users/" + userId;
        User user = userService.getUserById(userId);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        User deletedUser = super.mapFromJson(content, User.class);

        assertEquals(userId, deletedUser.getId());
        assertEquals(user.getFirstName(), deletedUser.getFirstName());
        assertEquals(user.getLastName(), deletedUser.getLastName());
        assertEquals(user.getPatronymicName(), deletedUser.getPatronymicName());
        assertEquals(true, deletedUser.getDeleted());
    }

    @Test
    //Test for contact deletion by user
    public void deleteContactByUser() throws Exception {
        createContactByUser();
        Long contactId = contactService.countAllContacts();
        String uri = "/api/contacts/" + contactId;
        Contact contact = contactService.getContactById(contactId);
        ContactDto contactDto = contactMapper.toContactDto(contact);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ContactDto deletedContactDto = super.mapFromJson(content, ContactDto.class);
        assertEquals(contactId, deletedContactDto.getId());
        assertEquals(contactDto.getPhoneNumber(), deletedContactDto.getPhoneNumber());
        assertEquals(contactDto.getType(), deletedContactDto.getType());
        assertEquals(contactDto.getUserId(), deletedContactDto.getUserId());
        assertEquals(true, deletedContactDto.getDeleted());
    }
}
