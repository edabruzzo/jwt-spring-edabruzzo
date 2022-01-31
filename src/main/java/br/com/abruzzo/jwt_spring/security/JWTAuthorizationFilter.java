package br.com.abruzzo.jwt_spring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String header = request.getHeader(SecurityConstraints.HEADER_TOKEN);

        if(header == null || header.startsWith(SecurityConstraints.TOKEN_PREFIX)) {
            try{
                throw new AusenciaTokenAutenticacao("Não foi fornecido token autenticação no request", this.logger);
            }catch(AusenciaTokenAutenticacao exception){
                exception.printStackTrace();
            }
            chain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request,response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(SecurityConstraints.HEADER_TOKEN);
        if(token == null){
            throw new AusenciaTokenAutenticacao("Token nulo");
        }

        String username = JWT.require(Algorithm.HMAC256(SecurityConstraints.SECRET.getBytes()))
                .build()
                .verify(token.replace(SecurityConstraints.TOKEN_PREFIX,""))
                .getSubject();

        return new UsernamePasswordAuthenticationToken(username,null,new ArrayList<>());

    }


}
