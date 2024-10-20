package org.example.orderservice.config;

import dto.OrderRequestDTO;
import event.OrderStatus;
import event.PaymentStatus;
import org.example.orderservice.entity.PurchaseOrder;
import org.example.orderservice.repository.OrderRepository;
import org.example.orderservice.service.OrderStatusPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Configuration
public class OrderStatusHandler {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderStatusPublisher publisher;

    @Transactional
    public void updateOrder(int id, Consumer<PurchaseOrder> consumer) {
        repository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(PurchaseOrder purchaseOrder) {
        OrderStatus orderStatus;
      if( PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus())){
          orderStatus =OrderStatus.ORDER_COMPLETED;
      }else {
          orderStatus =OrderStatus.ORDER_CANCEL;
          publisher.publishOrderEvent(convertEntityToDto(purchaseOrder),OrderStatus.ORDER_CANCEL);
      }
      purchaseOrder.setOrderStatus(orderStatus);
    }

    private OrderRequestDTO convertEntityToDto(PurchaseOrder purchaseOrder) {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setOrderId(purchaseOrder.getId());
        orderRequestDTO.setUserId(purchaseOrder.getUserId());
        orderRequestDTO.setAmount(purchaseOrder.getPrice());
        orderRequestDTO.setProductId(purchaseOrder.getProductId());
        return  orderRequestDTO;
    }


}
