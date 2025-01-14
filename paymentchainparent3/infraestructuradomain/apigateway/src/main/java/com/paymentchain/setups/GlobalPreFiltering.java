
package com.paymentchain.setups;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 *
 * @author ayyoub
 */
@Slf4j
@Component
public class GlobalPreFiltering implements GlobalFilter{
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalPreFiltering.class);
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Global prefilter executed");
        return chain.filter(exchange);
    }
    
}

