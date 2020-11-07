package ru.school.matcha.serializators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

@Slf4j
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

    public List<T> deserializeList(String json, Class<T> valueType) throws IOException {
        CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, valueType);
        return mapper.readValue(json, javaType);
    }
}
