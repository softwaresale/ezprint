package edu.purdue.whack.auth;

import com.microsoft.aad.msal4j.InteractiveRequestParameters;
import com.microsoft.aad.msal4j.PublicClientApplication;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class AuthService {

    private final PublicClientApplication application;
    private final InteractiveRequestParameters interactiveRequestParameters;
    private final Set<String> appScopes;

    public AuthService() throws MalformedURLException, URISyntaxException {
        appScopes = Set.of("Mail.Send", "User.read", "profile");

        application = PublicClientApplication.builder("961bf51f-b3c4-4b01-a002-3c66f0a3af10")
                .applicationName("EZPrint")
                .applicationVersion("0.0.1-SNAPSHOT")
                .authority("https://login.microsoftonline.com/4130bd39-7c53-419c-b1e5-8758d6d63f21")
                .build();

        interactiveRequestParameters = InteractiveRequestParameters.builder(new URI("http://localhost"))
                .scopes(appScopes)
                .build();
    }

    public String getAccessToken() throws ExecutionException, InterruptedException {
        return application.acquireToken(this.interactiveRequestParameters).get().accessToken();
    }
}
