package com.Api_Gateway.N;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


@Component
public class AuthenticationFilter implements WebFilter {

    private  static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);


    private final String SECRET_KEY = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    @Override
    public reactor.core.publisher.Mono<Void> filter(ServerWebExchange exchange
            , WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String path = request.getURI().getPath();
        if(path.startsWith("/auth-service/auth/login") || path.startsWith("/auth-service/auth/register")){
            return chain.filter(exchange);
        }

        if (request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                LOGGER.info("Received JWT token:{}", token);
                Claims claims = validateToken(token);
                if (claims != null) {
                    String username = claims.getSubject();
                    LOGGER.info("Authenticated User:{}", username);
                    List<Map<String,String>> roles = claims.get("roles",List.class);
                    String role = null;

                    if(roles != null && !roles.isEmpty()){
                        role = roles.get(0).get("authority"); // Extract the first authority in the list.

                        if(role !=null && role.startsWith("ROLE_")){
                            role = role.substring(5).toLowerCase();
                        }
                    }
                    LOGGER.info("User Role: {}", role);

                    ServerHttpRequest modifiedRequest = request.mutate()
                            .header("X-Authenticated-User", username)
                            .header("X-Authenticated-Role",role).build();
                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                } else {
                    LOGGER.warn("Invalid Jwt token");
                }

            } else {
                LOGGER.warn("Invalid Authorization Header Format");
            }
        }
        else {
            LOGGER.warn("No Authorization Header Found");
            }
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
        }

        private Claims validateToken(String token){
        try {
            return Jwts.parser().verifyWith(getSigningKey()).build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            LOGGER.error("Jwt Validation failed:{}", e.getMessage());
            return null;
        }
        }
        private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        }
}
