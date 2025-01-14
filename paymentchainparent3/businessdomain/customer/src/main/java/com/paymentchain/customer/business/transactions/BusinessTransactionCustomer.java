
package com.paymentchain.customer.business.transactions;

import com.fasterxml.jackson.databind.JsonNode;
import com.paymentchain.customer.common.CustomerRequestMapper;
import com.paymentchain.customer.common.CustomerResponseMapper;
import com.paymentchain.customer.dto.CustomerRequest;
import com.paymentchain.customer.dto.CustomerResponse;
import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.entities.CustomerProduct;
import com.paymentchain.customer.exception.BusinessRuleException;
import com.paymentchain.customer.respository.CustomerRepository;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

/**
 *
 * @author ayyoub
 */
@Service
public class BusinessTransactionCustomer {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerRequestMapper crm;

    @Autowired
    CustomerResponseMapper crsm;

    //webClient requires HttpClient library to work propertly     
    HttpClient client = HttpClient.create()
            //Connection Timeout: is a period within which a connection between a client and a server must be established
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            //Response Timeout: The maximun time we wait to receive a response after sending a request
            .responseTimeout(Duration.ofSeconds(1))
            // Read and Write Timeout: A read timeout occurs when no data was read within a certain 
            //period of time, while the write timeout when a write operation cannot finish at a specific time
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });

    public CustomerResponse get(String code) throws BusinessRuleException {
        // Buscar el cliente por su código
        Customer customer = customerRepository.findByCode(code);
        if (customer == null) {
            throw new BusinessRuleException(
                    "1025",
                    "Error validación, customer con codigo " + code + " no existe",
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        // Procesar productos del cliente
        List<CustomerProduct> products = customer.getProducts();
        if (products != null) {
            products.forEach(product -> {
                try {
                    String productName = getProductName(product.getProductId());
                    product.setProductName(productName);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(BusinessTransactionCustomer.class.getName()).log(Level.SEVERE, null, ex);
                    product.setProductName("Unknown"); // Asignar un valor por defecto si hay error
                }
            });
        }

        // Buscar todas las transacciones asociadas con el IBAN del cliente
        List<?> transactions = getTransactions(customer.getIban());
        customer.setTransactions(transactions);

        // Convertir el Customer a CustomerResponse
        return crsm.CustomerToCustomerResponse(customer);
    }

    public ResponseEntity<CustomerResponse> get(long id) {
        Optional<Customer> findById = customerRepository.findById(id);
        if (findById.isPresent()) {
            CustomerResponse response = crsm.CustomerToCustomerResponse(findById.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<CustomerResponse> post(CustomerRequest input) throws BusinessRuleException, UnknownHostException {
        // Mapear el DTO a la entidad
        Customer customer = crm.CustomerRequestToCustomer(input);

        // Validar los productos
        if (customer.getProducts() != null) {
            for (CustomerProduct product : customer.getProducts()) {
                String productName = getProductName(product.getProductId());
                if (productName.isBlank()) {
                    throw new BusinessRuleException(
                            "1025",
                            "Error validación, producto con id " + product.getProductId() + " no existe",
                            HttpStatus.PRECONDITION_FAILED
                    );
                } else {
                    product.setCustomer(customer);
                }
            }
        }

        // Guardar el cliente en la base de datos
        Customer savedCustomer = customerRepository.save(customer);

        // Mapear la entidad guardada a la respuesta DTO
        CustomerResponse response = crsm.CustomerToCustomerResponse(savedCustomer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Call Product Microservice , find a product by Id and return it name
     *
     * @param id of product to find
     * @return name of product if it was find
     */
    private String getProductName(long id) throws UnknownHostException {
        String name = "";
        try {
            WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                    .baseUrl("http://BUSINESSDOMAIN-PRODUCT/business-product/product")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultUriVariables(Collections.singletonMap("url", "http://BUSINESSDOMAIN-PRODUCT/business-product/product"))
                    .build();
            JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                    .retrieve().bodyToMono(JsonNode.class).block();
            name = block.get("name").asText();
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                return "";
            } else {
                throw new UnknownHostException(ex.getMessage());
            }
        }
        return name;
    }

    /**
     * Call Transaction Microservice and Find all transaction that belong to the
     * account give
     *
     * @param iban account number of the customer
     * @return All transaction that belong this account
     */
    private List<?> getTransactions(String iban) {
        try {
            WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                    .baseUrl("http://BUSINESSDOMAIN-TRANSACTION/business-transaction/transaction")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            return build.method(HttpMethod.GET)
                    .uri(uriBuilder -> uriBuilder
                    .path("/customer/transactions")
                    .queryParam("ibanAccount", iban)
                    .build())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.createException().flatMap(Mono::error))
                    .bodyToFlux(Object.class)
                    .collectList()
                    .onErrorResume(e -> {
                        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Error retrieving transactions", e);
                        return Mono.just(Collections.emptyList());
                    })
                    .block();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Unexpected error", e);
            return Collections.emptyList();
        }
    }

}
