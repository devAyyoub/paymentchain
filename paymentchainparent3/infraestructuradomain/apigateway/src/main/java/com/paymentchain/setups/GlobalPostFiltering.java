
package com.paymentchain.setups;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 *
 * @author ayyoub
 */
@Slf4j
@Configuration
public class GlobalPostFiltering {
    
     private static final Logger logger = LoggerFactory.getLogger(GlobalPostFiltering.class);

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    logger.info("Global Post Filter executed");
                }));
        };
    }
}
