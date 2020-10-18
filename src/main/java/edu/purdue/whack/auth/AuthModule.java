package edu.purdue.whack.auth;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class AuthModule extends AbstractModule {
    protected void configure() {
        //add configuration logic here
    }

    @Provides
    public AuthService authServiceProvider() {
        try {
            return new AuthService();
        } catch (Exception exe) {
            return null;
        }
    }

    @Provides
    public AuthProvider authProviderProvider() {
        return new AuthProvider(authServiceProvider());
    }
}
