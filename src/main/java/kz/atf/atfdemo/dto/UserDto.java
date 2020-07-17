package kz.atf.atfdemo.dto;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymicName;
    private Boolean deleted;
    private List<ContactDto> contacts = new LinkedList<>();
}
