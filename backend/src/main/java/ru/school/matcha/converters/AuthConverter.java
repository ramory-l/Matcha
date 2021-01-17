package ru.school.matcha.converters;

import ru.school.matcha.domain.Auth;
import ru.school.matcha.dto.AuthDto;
import ru.school.matcha.exceptions.MatchaException;

import static java.util.Objects.isNull;

public class AuthConverter extends Converter<AuthDto, Auth> {

    public AuthConverter() {
        super(AuthConverter::convertToEntity, AuthConverter::convertToDto);
    }

    private static AuthDto convertToDto(Auth source) {
        if (isNull(source)) {
            return null;
        }
        AuthDto result = new AuthDto();
        result.setUsername(source.getUsername());
        result.setPassword(source.getPassword());
        return result;
    }

    private static Auth convertToEntity(AuthDto source) {
        if (isNull(source)) {
            return null;
        }
        if (isNull(source.getUsername()) || isNull(source.getPassword())) {
            throw new MatchaException("Username or password is incorrect");
        }
        Auth result = new Auth();
        result.setUsername(source.getUsername());
        result.setPassword(source.getPassword());
        return result;
    }
}
