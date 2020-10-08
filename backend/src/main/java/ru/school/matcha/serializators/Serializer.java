package ru.school.matcha.serializators;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class Serializer<T>  {

    private final ObjectMapper mapper;

    public Serializer() {
        this.mapper = new ObjectMapper();
    }

    public String serialize(T t) throws IOException {
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, t);
        return writer.toString();
    }

    public T deserialize(String json, Class<T> valueType) throws IOException {
        StringReader reader = new StringReader(json);
        return mapper.readValue(reader, valueType);
    }
}
