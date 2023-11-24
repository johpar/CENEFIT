package org.hdcd.common.security.authentication;


import java.util.Collection;

import org.hdcd.common.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("authenticate");

        CustomUsernamePasswordAuthenticationToken token = (CustomUsernamePasswordAuthenticationToken)authentication;
        String userName = token.getName();
        String secretNumber = token.getSecretNumber();

        log.info("authenticate userName " + userName);

        UserDetails user = userDetailsService.loadUserByUsername(userName);
        log.info("user: {}", user);

        if(user == null) {
            throw new UsernameNotFoundException("Invalid username/password");
        }

        String encodedPassword = user.getPassword();
        String credentials = (String)token.getCredentials();

        log.info("authenticate encodedPassword " + encodedPassword);
        log.info("authenticate token.getCredentials() " + credentials);
        log.info("authenticate encodedCredentials " + passwordEncoder.matches(credentials, encodedPassword));

        if(!passwordEncoder.matches(credentials, encodedPassword)) {
            throw new BadCredentialsException("Invalid username/password");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        log.info("authorities: {}", authorities);

        return new CustomUsernamePasswordAuthenticationToken(user, encodedPassword, secretNumber, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomUsernamePasswordAuthenticationToken.class.equals(authentication);
    }

    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
