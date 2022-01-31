package br.com.abruzzo.jwt_spring.security;

import br.com.abruzzo.jwt_spring.dto.UserDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @link https://cloud.google.com/endpoints/docs/openapi/service-account-authentication#java
 * @link https://www.baeldung.com/spring-security-extra-login-fields
 * @link https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/headers.html
 * @link https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html
 */
public class JWTAuthenticatorFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;

    public JWTAuthenticatorFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UserDTO userDTO = null;
        try {
            userDTO = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDTO.getUsername(),
                userDTO.getPassword(),
                new ArrayList<>()
        );

        return this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    }

    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {

        return null;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authenticationResult) throws IOException, ServletException {


        String token = JWT.create()
                .withSubject(authenticationResult.getName())
                .withExpiresAt(new Date((System.currentTimeMillis() + SecurityConstraints.EXPIRATION_TIME)))
                .sign(Algorithm.HMAC256(SecurityConstraints.SECRET.getBytes()));

        response.addHeader(SecurityConstraints.HEADER_TOKEN, SecurityConstraints.TOKEN_PREFIX + token);

    }

}
