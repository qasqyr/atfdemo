package kz.atf.atfdemo.mapper;

import kz.atf.atfdemo.dto.UserDto;
import kz.atf.atfdemo.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
}
