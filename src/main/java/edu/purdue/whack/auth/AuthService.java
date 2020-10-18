package edu.purdue.whack.auth;

import com.microsoft.aad.msal4j.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class AuthService {

    private final PublicClientApplication application;
    private final InteractiveRequestParameters interactiveRequestParameters;
    private final Set<String> appScopes;

    public AuthService() throws MalformedURLException, URISyntaxException {
        appScopes = Set.of("Mail.Send", "User.read", "profile");
        TokenCache cache = new TokenCache();

        application = PublicClientApplication.builder("961bf51f-b3c4-4b01-a002-3c66f0a3af10")
                .applicationName("EZPrint")
                .applicationVersion("0.0.1-SNAPSHOT")
                .authority("https://login.microsoftonline.com/4130bd39-7c53-419c-b1e5-8758d6d63f21")
                .build();

        interactiveRequestParameters = InteractiveRequestParameters.builder(new URI("http://localhost"))
                .scopes(appScopes)
                .build();
    }

    private Optional<IAuthenticationResult> silentAuth() {
        SilentParameters parameters = SilentParameters.builder(this.appScopes).build();
        try {
            var accessToken = application.acquireTokenSilently(parameters).get();
            return Optional.of(accessToken);
        } catch (Exception exe) {
            exe.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<String> getAccessTokenSilently() {
        return silentAuth().map(IAuthenticationResult::accessToken);
    }

    public Optional<String> getAccessToken() {
        try {
            String token = application.acquireToken(this.interactiveRequestParameters).get().accessToken();
            return Optional.of(token);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void logout() throws ExecutionException, InterruptedException {
        var auth = silentAuth();
        if (auth.isPresent()) {
            var result = auth.get();
            application.removeAccount(result.account()).get();
        }
    }
}
