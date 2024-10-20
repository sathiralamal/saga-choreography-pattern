package org.example.orderservice.service;

import dto.OrderRequestDTO;
import org.example.orderservice.entity.PurchaseOrder;

import java.util.List;

public interface OrderServiceImp {
   public PurchaseOrder createOrder(OrderRequestDTO orderRequestDTO);
   public List<PurchaseOrder> getAllOrder();
}
