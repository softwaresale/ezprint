package edu.purdue.whack.email;

import edu.purdue.whack.auth.AuthProvider;
import okhttp3.*;

import javax.inject.Inject;

public class EmailService {

    private final OkHttpClient httpClient;
    private final AuthProvider authProvider;

    @Inject
    public EmailService(AuthProvider authProv) {
        this.authProvider = authProv;
        this.httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request authedRequest = authProvider.authenticateRequest(chain.request());
                    return chain.proceed(authedRequest);
                })
                .build();
    }
}
