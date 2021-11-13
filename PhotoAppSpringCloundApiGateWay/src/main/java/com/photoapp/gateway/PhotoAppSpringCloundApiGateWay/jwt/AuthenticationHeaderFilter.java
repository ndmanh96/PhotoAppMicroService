package com.photoapp.gateway.PhotoAppSpringCloundApiGateWay.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationHeaderFilter extends AbstractGatewayFilterFactory<AuthenticationHeaderFilter.Config> {

	@Autowired
	private Environment env;
	
	public AuthenticationHeaderFilter() {
		super(Config.class);
	}

	public static class Config {
		// Put configuration properties here
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {

			ServerHttpRequest request = exchange.getRequest();

			if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
			}

			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwtCode = authorizationHeader.replace("Bearer", ""); // spring.cloud.gateway.routes[4].predicates[2]=Header=Authorization,
																		// Bearer (.*)
																		// Nen can replace de lay jwtcode sau Bearer
			
			if (!isJwtValid(jwtCode)) {
				
				return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
			}
			
			
			return chain.filter(exchange);
		};
	}

	private Mono<Void> onError(ServerWebExchange exchange, String string, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}

	// check valid JWT
	private boolean isJwtValid(String jwtCode) {
		boolean returnValue = true;
		
		String subject = null;
		
		try {
			subject = Jwts.parser()
					.setSigningKey(env.getProperty("token.secret"))
					.parseClaimsJws(jwtCode)
					.getBody()
					.getSubject();
			
		} catch (Exception e) {
			returnValue = false;
		}
		
		
		if (subject == null || subject.isEmpty()) {
			returnValue = false;
		}

		return returnValue;
	}
}
