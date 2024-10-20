package org.example.orderservice.config;

import event.OrderEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class OrderPublisherConfig {

    @Bean
    public Sinks.Many<OrderEvent> orderSinks(){
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    public Supplier<Flux<OrderEvent>> orderSuplier(Sinks.Many<OrderEvent> sinks){
        return sinks::asFlux;
    }
}
