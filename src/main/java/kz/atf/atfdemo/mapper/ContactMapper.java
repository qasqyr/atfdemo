package kz.atf.atfdemo.mapper;

import kz.atf.atfdemo.dto.ContactDto;
import kz.atf.atfdemo.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    @Mapping(target = "userId", source = "user.id")
    ContactDto toContactDto(Contact contact);
}
