package org.example.orderservice.config;

import event.PaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EventConsumerConfig {

    @Autowired
    private OrderStatusHandler handler;

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer(){
        return (payment)->handler.updateOrder(payment.getPaymentRequestDTO().getOrderId(),po->{
            po.setPaymentStatus(payment.getPaymentStatus());
        });
    }

}
