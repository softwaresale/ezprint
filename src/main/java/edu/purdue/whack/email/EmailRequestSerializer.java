package edu.purdue.whack.email;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class EmailRequestSerializer extends StdSerializer<EmailRequest> {

    public EmailRequestSerializer() {
        this(null);
    }

    protected EmailRequestSerializer(Class<EmailRequest> t) {
        super(t);
    }

    @Override
    public void serialize(EmailRequest emailRequest, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart("message");
        jsonGenerator.writeArrayFieldStart("toRecipients");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart("emailAddress");
        jsonGenerator.writeStringField("address", emailRequest.getEmailAddr());
        jsonGenerator.writeEndObject(); // End emailAddress
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndArray(); // End to recipients
        jsonGenerator.writeObjectField("attachments", emailRequest.getAttachments());
        jsonGenerator.writeEndObject(); // End message
        jsonGenerator.writeEndObject(); // End payload
    }
}
