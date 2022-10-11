package edu.kmaooad;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BotRequestMapperTest {

    BotRequestMapper mapper = new BotRequestMapper(new ObjectMapper());
    @Test
    void mapFromTest() {
//        String testJson = "{\"update_id\":805877849,\n" +
//                " \"message\":{\n" +
//                "\t\"message_id\":4,\n" +
//                "\t\"from\":{\n" +
//                "\t\t\"id\":336588268,\n" +
//                "\t\t\"is_bot\":false,\n" +
//                "\t\t\"first_name\":\"\\u0412\\u0430\\u043d\\u0451\\u043a\",\n" +
//                "\t\t\"username\":\"ivaannne\",\n" +
//                "\t\t\"language_code\":\"uk\"\n" +
//                "\t\t},\n" +
//                "\t\"chat\":{\n" +
//                "\t\t\"id\":336588268,\n" +
//                "\t\t\"first_name\":\"\\u0412\\u0430\\u043d\\u0451\\u043a\",\n" +
//                "\t\t\"username\":\"ivaannne\",\n" +
//                "\t\t\"type\":\"private\"\n" +
//                "\t\t},\n" +
//                "\t\"date\":1664530834,\n" +
//                "\t\"text\":\"bot\"\n" +
//                "\t}\n" +
//                "}";
//
//        BotRequest mappedRequest = mapper.mapFrom(testJson);
//
//        assertEquals(4L, mappedRequest.getMessage().getMessage_id());
//        assertEquals("bot", mappedRequest.getMessage().getText());
    }
}