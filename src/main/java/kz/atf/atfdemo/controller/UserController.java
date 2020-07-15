package kz.atf.atfdemo.controller;

import kz.atf.atfdemo.dto.ContactDto;
import kz.atf.atfdemo.dto.UserDto;
import kz.atf.atfdemo.mapper.ContactMapper;
import kz.atf.atfdemo.mapper.UserMapper;
import kz.atf.atfdemo.model.Contact;
import kz.atf.atfdemo.model.User;
import kz.atf.atfdemo.response.ListResponse;
import kz.atf.atfdemo.service.ContactService;
import kz.atf.atfdemo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
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
    public ResponseEntity<ListResponse<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = users.stream().map(userMapper::toUserDto).collect(Collectors.toList());
        return ResponseEntity.ok(new ListResponse<>(userDtos));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        User user;
        try {
            user = userService.getUserById(userId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        User user = userMapper.toUser(userDto);
        userService.save(user);
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<UserDto> deleteUserById(@PathVariable Long userId) {
        User user;
        try {
            user = userService.deleteUserById(userId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @GetMapping("/users/{userId}/contacts")
    public ResponseEntity<ListResponse<ContactDto>> getAllContactsByUserId(@PathVariable Long userId) {
        List<Contact> contacts = contactService.getAllContactsByUserId(userId);
        List<ContactDto> contactDtos = contacts.stream().map(contactMapper::toContactDto).collect(Collectors.toList());
        return ResponseEntity.ok(new ListResponse<>(contactDtos));
    }
}
