package edu.purdue.whack.auth;

import com.microsoft.graph.httpcore.ICoreAuthenticationProvider;
import okhttp3.Request;

import javax.inject.Inject;
import java.util.Optional;

public class AuthProvider implements ICoreAuthenticationProvider {

    private final AuthService authService;

    @Inject
    public AuthProvider(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Request authenticateRequest(Request request) {
        Optional<String> token = authService.getAccessTokenSilently();
        return token
                .map(s -> request.newBuilder()
                    .addHeader("Authorization", "Bearer " + s)
                    .build()
                )
                .orElse(authService.getAccessToken().map(tok -> request.newBuilder().addHeader("Authorization", "Bearer " + tok).build()).orElse(null));
    }
}
