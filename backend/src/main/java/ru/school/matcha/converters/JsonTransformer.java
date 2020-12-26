package ru.school.matcha.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ResponseTransformer;

import java.io.StringWriter;

public class JsonTransformer implements ResponseTransformer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StringWriter stringWriter = new StringWriter();

    @Override
    public synchronized String render(Object model) throws Exception {
        objectMapper.writeValue(cleanWriter(stringWriter), model);
        return stringWriter.toString();
    }

    private StringWriter cleanWriter(StringWriter stringWriter) {
        StringBuffer sb = stringWriter.getBuffer();
        sb.delete(0, sb.length());
        return stringWriter;
    }
}
