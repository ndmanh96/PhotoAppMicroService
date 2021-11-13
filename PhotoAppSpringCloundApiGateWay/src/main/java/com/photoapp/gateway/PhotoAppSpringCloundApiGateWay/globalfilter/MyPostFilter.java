package com.photoapp.gateway.PhotoAppSpringCloundApiGateWay.globalfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class MyPostFilter implements GlobalFilter, Ordered {
	private final Logger logger = LoggerFactory.getLogger(MyPostFilter.class);
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			//add some code
			logger.info("Global Post-filter executed...");
			
			
		}));
	}
	
	// Thay cho @Order
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
