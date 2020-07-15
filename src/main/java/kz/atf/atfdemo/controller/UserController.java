package kz.atf.atfdemo.controller;

import kz.atf.atfdemo.dto.ContactDto;
import kz.atf.atfdemo.dto.UserDto;
import kz.atf.atfdemo.mapper.ContactMapper;
import kz.atf.atfdemo.mapper.UserMapper;
import kz.atf.atfdemo.model.Contact;
import kz.atf.atfdemo.model.User;
import kz.atf.atfdemo.service.ContactService;
import kz.atf.atfdemo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private UserService userService;
    private UserMapper userMapper;
    private ContactService contactService;
    private ContactMapper contactMapper;

    public UserController(UserService userService, UserMapper userMapper, ContactService contactService, ContactMapper contactMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.contactService = contactService;
        this.contactMapper = contactMapper;
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    @PostMapping("/users")
    public UserDto saveUser(@RequestBody UserDto userDto) {
        User user = userMapper.toUser(userDto);
        userService.save(user);
        return userMapper.toUserDto(user);
    }

    @GetMapping("/users/{userId}/contacts")
    public List<ContactDto> getAllContactsByUserId(@PathVariable Long userId) {
        List<Contact> contacts = contactService.getAllContactsByUserId(userId);
        return contacts.stream().map(contactMapper::toContactDto).collect(Collectors.toList());
    }
}
