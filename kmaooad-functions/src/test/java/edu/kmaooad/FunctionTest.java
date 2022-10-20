package edu.kmaooad;

import com.microsoft.azure.functions.*;
import org.mockito.stubbing.Answer;

import java.util.*;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


/**
 * Unit test for Function class.
 */
public class FunctionTest {

    private static final String BAD_RESPONSE_BODY = "Request body error. Please fix your request body";

    private HttpRequestMessage<Optional<String>> getReq(Optional<String> queryBody) {
        // Setup
        @SuppressWarnings("unchecked")
        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

        doReturn(queryBody).when(req).getBody();

        doAnswer((Answer<HttpResponseMessage.Builder>) invocation -> {
            HttpStatus status = (HttpStatus) invocation.getArguments()[0];
            return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        return req;
    }

    private ExecutionContext getContext() {
        // Setup
        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();
        return context;
    }

    @Test
    public void testFunctionWithoutBody() {
        // Invoke
        final HttpResponseMessage ret = new Function().run(getReq(Optional.empty()), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE_BODY, ret.getBody());
    }

    @Test
    public void testFunctionWithMessageWithoutMessageIdInBody(){
        // Invoke
        final HttpResponseMessage ret = new Function().run(getReq(Optional.of("{\n" +
                "    \"message\": {}\n" +
                "}")), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE_BODY, ret.getBody());
    }

    @Test
    public void testFunctionWithMessageIdWithoutMessageInBody(){
        // Invoke
        final HttpResponseMessage ret = new Function().run(getReq(Optional.of("{\n" +
                "    \"message\": {\n" +
                "        \"message_id\": 12345\n" +
                "    }\n" +
                "}")), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE_BODY, ret.getBody());
    }


    @Test
    public void testFunctionWithCorrectBody(){
        int ID = 12345;
        // Invoke
        final HttpResponseMessage ret = new Function().run(getReq(Optional.of("{\n" +
                "    \"message\": {\n" +
                "        \"message_id\": " + ID + ",\n" +
                "        \"text\": \"TEST\"\n" +
                "    }\n" +
                "}")), getContext());
        // Verify
        assertEquals(HttpStatus.OK, ret.getStatus());
        assertEquals("message_id :  " + ID, ret.getBody());
    }

}
