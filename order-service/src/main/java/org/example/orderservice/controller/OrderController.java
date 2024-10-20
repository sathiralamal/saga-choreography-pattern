package org.example.orderservice.controller;

import dto.OrderRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.entity.PurchaseOrder;
import org.example.orderservice.service.OrderService;
import org.example.orderservice.service.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImp orderService;

    @PostMapping("/create")
    public ResponseEntity<PurchaseOrder> createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        PurchaseOrder response = null;
        try {
            response =  orderService.createOrder(orderRequestDTO);
        }catch (Exception e){
            log.debug("Create order : ",e.getMessage());
            response = null;
        }
      return   ResponseEntity.ok(response);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<PurchaseOrder>> getOrders(){
        List<PurchaseOrder> allOrder =orderService.getAllOrder();
        return  ResponseEntity.ok(allOrder);
    }
}
