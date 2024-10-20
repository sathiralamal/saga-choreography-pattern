package org.example.paymentservice.controller;

import org.example.paymentservice.entity.UserBalance;
import org.example.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/userbalance")
    public ResponseEntity<List<UserBalance>> getAllUserBalance(){
        return ResponseEntity.ok(paymentService.getAllUseBalance());
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Test API");
    }
}
