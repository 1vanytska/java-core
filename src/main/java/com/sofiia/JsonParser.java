package com.sofiia;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonParser{
    private static final ObjectMapper DEFAULT_MAPPER;

    static {
        ObjectMapper mapper = new ObjectMapper();
        // Не створювати exception, якщо json має додаткові поля
        // без серіалізації. Це корисно, коли ви хочете використовувати pojo
        // для десеріалізації та дбає лише про частину json
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Ігноруйте нульові значення під час запису json.
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // Записувати час як рядок замість long, щоб його було зрозуміло людині.
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        mapper.registerModule(new JavaTimeModule());
        DEFAULT_MAPPER = mapper;
    }

    public static ObjectMapper getMapper() {
        return DEFAULT_MAPPER;
    }
}