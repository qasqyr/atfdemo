package kz.atf.atfdemo.mapper;

import kz.atf.atfdemo.dto.ContactDto;
import kz.atf.atfdemo.model.Contact;
import kz.atf.atfdemo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    @Mapping(target = "userId", source = "user.id")
    ContactDto toContactDto(Contact contact);

    Contact toContact(ContactDto contactDto);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", source = "contactDto.id")
    @Mapping(target = "phoneNumber", source = "contactDto.phoneNumber")
    @Mapping(target = "type", source = "contactDto.type")
    @Mapping(target = "deleted", source = "contactDto.deleted")
    Contact toContact(User user, ContactDto contactDto);
}
