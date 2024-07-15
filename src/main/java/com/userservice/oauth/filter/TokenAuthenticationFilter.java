package com.userservice.oauth.filter;
import com.userservice.oauth.config.TokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class TokenAuthenticationFilter extends AbstractGatewayFilterFactory<TokenAuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private TokenValidator tokenValidator;

    public TokenAuthenticationFilter() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                //check if header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header details");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
//                    //can use other API to validate token
//                    restTemplate.getForObject("http://localhost:8080//validate?token" + authHeader, String.class);
                    tokenValidator.validateToken(authHeader);

                } catch (Exception e) {
                    log.error("Unauthorized Access!!!");
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}