
package com.paymentchain.setups;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.net.HttpHeaders;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author ayyoub
 */
@Slf4j
@Component
public class AuthenticationFiltering extends AbstractGatewayFilterFactory<AuthenticationFiltering.Config> {

    @Autowired
    private WebClient.Builder webClientBuilder;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFiltering.class);

    //private final WebClient.Builder webClientBuilder;
    public AuthenticationFiltering(WebClient.Builder webclientBuilder) {
        super(Config.class);
        this.webClientBuilder = webclientBuilder;
    }

    // @Override
    // public GatewayFilter apply(Config config) {
    //     return new OrderedGatewayFilter((exchange, chain) -> {
    //          if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
    //                     throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing  Authorization header");
    //          }
    //          String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
    //                 String[] parts = authHeader.split(" ");
    //                 if (parts.length != 2 || !"Bearer".equals(parts[0])) {
    //                     throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad Authorization structure");
    //                 }
    //                 return  webClientBuilder.build()
    //                         .get()
    //                         .uri("http://keycloak/roles").header(HttpHeaders.AUTHORIZATION, parts[1])
    //                         .retrieve()
    //                          .bodyToMono(JsonNode.class)
    //                          .map(response -> {
    //                              if(response != null){
    //                                logger.info("See Objects: " + response);
    //                                  //check for Partners rol
    //                                if(response.get("Partners") == null || StringUtils.isEmpty(response.get("Partners").asText())){
    //                                     throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Role Partners missing");
    //                                }
    //                              }else{
    //                                  throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Roles missing");
    //                              }
    //                          return exchange;
    //                  })
    //                    .onErrorMap(error -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Communication Error", error.getCause());})
    //                   .flatMap(chain::filter);
    //             },1);
    // }
//    @Override
//    public GatewayFilter apply(Config config) {
//        return new OrderedGatewayFilter((exchange, chain) -> {
//            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                logger.error("Request missing Authorization header. URI: {}", exchange.getRequest().getURI());
//                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization header is missing from the request");
//            }
//
//            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//            String[] parts = authHeader.split(" ");
//            if (parts.length != 2 || !"Bearer".equalsIgnoreCase(parts[0])) {
//                logger.error("Invalid Authorization structure. Expected 'Bearer <token>', got: {}", authHeader);
//                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header structure. Expected 'Bearer <token>'");
//            }
//
//            String token = authHeader.substring(7);  // Remove "Bearer " prefix
//
//            try {
//                WebClient client = webClientBuilder
//                        .clientConnector(new ReactorClientHttpConnector()) // Configura si necesitas un HttpClient personalizado
//                        .baseUrl("http://KEYCLOAK")
//                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                        .build();
//
//                return client.method(HttpMethod.GET)
//                        .uri("/roles")
//                        .retrieve()
//                        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
//                                response -> response.createException().flatMap(Mono::error))
//                        .bodyToMono(JsonNode.class)
//                        .flatMap(response -> {
//                            if (response != null) {
//                                logger.info("See Objects: " + response);
//                                if (response.get("Partners") == null || StringUtils.isEmpty(response.get("Partners").asText())) {
//                                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Role Partners missing");
//                                }
//                            } else {
//                                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Roles missing");
//                            }
//                            return exchange.getResponse().setComplete();  // Return a completion signal
//                        })
//                        .onErrorResume(error -> {
//                            logger.error("Communication Error", error);
//                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Communication Error", error.getCause());
//                        });
//            } catch (Exception e) {
//                logger.error("Unexpected error", e);
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", e);
//            }
//        }, 1);
//    }
    @Override
    public GatewayFilter apply(Config config) {

        return new OrderedGatewayFilter((exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing  Authorization header");
            }


            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] parts = authHeader.split(" ");
            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad Authorization structure");
            }

            return  webClientBuilder.build()
                    .get()
                    .uri("http://KEYCLOAK/roles").header(HttpHeaders.AUTHORIZATION, parts[1])
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .map(response -> {
                        if(response != null){
                            logger.info("See Objects: " + response);
                            //check for Partners rol
                            if(response.get("Partners") == null || StringUtils.isEmpty(response.get("Partners").asText())){
                                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Role Partners missing");
                            }
                        }else{
                            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Roles missing");
                        }
                        return exchange;
                    })
                    .onErrorMap(error -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Communication Error", error.getCause());})
                    .flatMap(chain::filter);
        },1);
    }

    public static class Config {

    }

}
