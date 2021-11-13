package com.photoapp.gateway.PhotoAppSpringCloundApiGateWay.globalfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import reactor.core.publisher.Mono;

@Configuration
public class GlobalFiltersConfiguration {
	
	private final Logger logger = LoggerFactory.getLogger(MyPostFilter.class);
	
	@Order(1)
	@Bean
	public GlobalFilter secondFilter() {
		return (exchange, chain) -> {
			logger.info("My Second global pre-filter is executed.....");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				//add some code
				logger.info("My Second global post-filter executed...");
				
				
			}));
		};
	}
	
	@Order(2)
	@Bean
	public GlobalFilter thirdFilter() {
		return (exchange, chain) -> {
			logger.info("My Third global pre-filter is executed.....");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				//add some code
				logger.info("My Third global post-filter executed...");
				
				
			}));
		};
	}
}
