package edu.purdue.whack.email;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class AttachmentSerializer extends StdSerializer<EmailRequest.Attachment> {

    public AttachmentSerializer() {
        this(null);
    }

    protected AttachmentSerializer(Class<EmailRequest.Attachment> type) {
        super(type);
    }

    @Override
    public void serialize(EmailRequest.Attachment attachment, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("@odata.type", "#microsoft.graph.fileAttachment");
        jsonGenerator.writeStringField("name", attachment.getFile().getName());
        jsonGenerator.writeStringField("contentType", "application/pdf"); // TODO Change this later
        jsonGenerator.writeBinaryField("contentBytes", attachment.readBytes());
        jsonGenerator.writeEndObject();
    }
}
