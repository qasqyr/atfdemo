package kz.atf.atfdemo.dto;

import lombok.Data;

@Data
public class ContactDto {
    private Long id;
    private String phoneNumber;
    private String type;
    private Long userId;
    private Boolean deleted;
}
