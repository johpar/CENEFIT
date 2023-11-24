package org.hdcd.common.security.authentication;


import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public final class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = -5138870746127783L;

    private final String secretNumber;

    public CustomUsernamePasswordAuthenticationToken(String principal, String credentials, String secretNumber) {
        super(principal, credentials);

        this.secretNumber = secretNumber;
    }

    public CustomUsernamePasswordAuthenticationToken(UserDetails principal, String credentials, String secretNumber,
                                                     Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);

        this.secretNumber = secretNumber;
    }

    public String getSecretNumber() {
        return secretNumber;
    }

}
