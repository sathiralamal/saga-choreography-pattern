package org.example.orderservice.service;

import dto.OrderRequestDTO;
import event.OrderStatus;
import event.PaymentStatus;
import jakarta.transaction.Transactional;
import org.example.orderservice.entity.PurchaseOrder;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService  implements OrderServiceImp{
    @Autowired
    private OrderStatusPublisher orderStatusPublisher;
    @Autowired
    private OrderRepository orderRepository;

    public OrderService(OrderStatusPublisher orderStatusPublisher) {
        this.orderStatusPublisher = orderStatusPublisher;
    }

    @Transactional
    @Override
    public PurchaseOrder createOrder(OrderRequestDTO orderRequestDTO) {
        PurchaseOrder order = convertDtoToEntity(orderRequestDTO);
        orderRequestDTO.setOrderId(order.getId());
        orderStatusPublisher.publishOrderEvent(orderRequestDTO,OrderStatus.ORDER_CREATED);
        return order;
    }

    @Override
    public List<PurchaseOrder> getAllOrder() {
        return orderRepository.findAll();
    }


    private PurchaseOrder convertDtoToEntity(OrderRequestDTO orderRequestDTO){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setProductId(orderRequestDTO.getProductId());
        purchaseOrder.setUserId(orderRequestDTO.getUserId());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(orderRequestDTO.getAmount());
        return  purchaseOrder;
    }
}
