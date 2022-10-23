package edu.kmaooad;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Constants {

    private Constants() {}

    public static final String BAD_RESPONSE_BODY = "Request body error. Please fix your request body";

    public static final ObjectMapper APPLICATION_OBJECT_MAPPER = new ObjectMapper();

    public static final String REQUEST_WITHOUT_MESSAGE_ID = "Illegal request, message_id not present";

}
