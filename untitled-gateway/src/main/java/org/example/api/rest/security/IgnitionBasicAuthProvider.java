package org.example.api.rest.security;

import com.inductiveautomation.ignition.common.user.BasicAuthChallenge;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class IgnitionBasicAuthProvider implements AuthenticationProvider {

    private final GatewayContext gatewayContext;

    @Autowired
    public IgnitionBasicAuthProvider(GatewayContext gatewayContext) {
        this.gatewayContext = gatewayContext;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var username = authentication.getName();
        var password = authentication.getCredentials().toString();

        var userSourceName = "default";
        var userSource = gatewayContext.getUserSourceManager().getProfile(userSourceName);

        try {
            // authenticate the user
            var authenticatedUser = userSource.authenticate(new BasicAuthChallenge(username, password));

            // map the user's roles
            Objects.requireNonNull(authenticatedUser, "User not found");
            var roles = authenticatedUser.getRoles();
            var authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            // return the authenticated user
            return new UsernamePasswordAuthenticationToken(username, password, authorities);

        } catch (Exception e) {
            throw new BadCredentialsException("Authentication failed", e);
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
