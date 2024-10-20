package org.example.paymentservice.config;

import event.OrderEvent;
import event.OrderStatus;
import event.PaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.example.paymentservice.service.PaymentService;

import java.util.function.Function;

@Configuration
public class PaymentConsumerConfig {
    @Autowired
    private PaymentService paymentService;


    @Bean
    public Function<Flux<OrderEvent>,Flux<PaymentEvent>> paymentProcessor(){
        return  orderEventFlux -> orderEventFlux.flatMap(this::paymentProcesses);
    }

    private Mono<PaymentEvent> paymentProcesses(OrderEvent orderEvent){
        if(OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())){
            return Mono.fromSupplier(()->this.paymentService.newOrderEvent(orderEvent));
        }else {
            return Mono.fromRunnable(()->this.paymentService.cancelOrderEvent(orderEvent));
        }
    }
}
