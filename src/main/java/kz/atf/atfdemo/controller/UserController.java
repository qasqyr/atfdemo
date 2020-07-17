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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final ContactService contactService;
    private final ContactMapper contactMapper;

    @GetMapping("/users")
    public ResponseEntity<ListResponse<UserDto>> getAllUsers(@RequestParam(defaultValue = "false") Boolean deleted) {
        List<User> users = userService.getAllUsersByDeleted(deleted);
        List<UserDto> userDtos = users.stream().map(userMapper::toUserDto).collect(Collectors.toList());
        return ResponseEntity.ok(new ListResponse<>(userDtos));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        User user;
        try {
            user = userService.getUserById(userId);
        } catch (NoSuchElementException e) {
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
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @GetMapping("/users/{userId}/contacts")
    public ResponseEntity<ListResponse<ContactDto>> getAllContactsByUserId(@PathVariable Long userId, @RequestParam(defaultValue = "false") Boolean deleted) {
        List<Contact> contacts = contactService.getAllContactsByUserIdAndDeleted(userId, deleted);
        List<ContactDto> contactDtos = contacts.stream().map(contactMapper::toContactDto).collect(Collectors.toList());
        return ResponseEntity.ok(new ListResponse<>(contactDtos));
    }
}
