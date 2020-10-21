package ru.school.matcha.converters;

import ru.school.matcha.domain.Credentials;
import ru.school.matcha.dto.CredentialsDto;

import static java.util.Objects.isNull;

public class CredentialsConverter extends Converter<CredentialsDto, Credentials> {

    public CredentialsConverter() {
        super(CredentialsConverter::convertToEntity, CredentialsConverter::convertToDto);
    }

    private static CredentialsDto convertToDto(Credentials source) {
        if (isNull(source)) {
            return null;
        }
        CredentialsDto result = new CredentialsDto();
        result.setUsername(source.getUsername());
        result.setEmail(source.getEmail());
        result.setPassword(source.getPassword());
        return result;
    }

    private static Credentials convertToEntity(CredentialsDto source) {
        if (isNull(source)) {
            return null;
        }
        Credentials result = new Credentials();
        result.setUsername(source.getUsername());
        result.setEmail(source.getEmail());
        result.setPassword(source.getPassword());
        return result;
    }

}
