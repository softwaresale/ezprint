package edu.purdue.whack.email;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.purdue.whack.auth.AuthProvider;
import okhttp3.*;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class EmailService {

    private final OkHttpClient httpClient;
    private final AuthProvider authProvider;
    private final ObjectMapper mapper;
    private final String mailEndpoint;

    @Inject
    public EmailService(AuthProvider authProv) {
        this.authProvider = authProv;
        this.httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request authedRequest = authProvider.authenticateRequest(chain.request());
                    return chain.proceed(authedRequest);
                })
                .build();

        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(EmailRequest.class, new EmailRequestSerializer());
        module.addSerializer(EmailRequest.Attachment.class, new AttachmentSerializer());
        mapper.registerModule(module);

        this.mailEndpoint = "https://graph.microsoft.com/v1.0/me/sendMail";
    }

    public boolean sendPrintRequest(Collection<File> files) throws IOException {
        String payload = null;
        try {
            payload = this.getRequestPayload(files);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }

        Request request = new Request.Builder()
                .url(this.mailEndpoint)
                .post(RequestBody.create(MediaType.parse("application/json"), payload))
                .build();

        Response response = this.httpClient.newCall(request).execute();
        return response.isSuccessful();
    }

    private String getRequestPayload(Collection<File> files) throws JsonProcessingException {
        EmailRequest request = new EmailRequest("chucks.8090@gmail.com", files);
        String payload = this.mapper.writeValueAsString(request);
        return payload;
    }
}
