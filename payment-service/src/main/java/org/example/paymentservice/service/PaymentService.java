package org.example.paymentservice.service;



import dto.OrderRequestDTO;
import dto.PaymentRequestDTO;
import event.OrderEvent;
import event.PaymentEvent;
import event.PaymentStatus;
import jakarta.annotation.PostConstruct;
import org.example.paymentservice.entity.UserBalance;
import org.example.paymentservice.entity.UserTransaction;
import org.example.paymentservice.repository.UserBalanceRepository;
import org.example.paymentservice.repository.UserTransactionRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
public class PaymentService {
    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserTransactionRepositiry userTransactionRepositiry;

    @PostConstruct
    public void initUserValanceInDb(){
        userBalanceRepository.saveAll(Stream.of(
                new UserBalance(101,50000),
                new UserBalance(102,40000),
                new UserBalance(103,30000),
                new UserBalance(104,254000),
                new UserBalance(105,4000)).toList());
    }

    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDTO orderRequestDTO = orderEvent.getOrderRequestDTO();
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(orderRequestDTO.getOrderId(),orderRequestDTO.getUserId(),orderRequestDTO.getAmount());
       return userBalanceRepository.findById(orderRequestDTO.getUserId())
                .filter(ub->ub.getPrice()>orderRequestDTO.getAmount())
                .map(ub->{
                    ub.setPrice(ub.getPrice()- orderRequestDTO.getAmount());
                    userTransactionRepositiry.save(new UserTransaction(
                            orderRequestDTO.getOrderId(),
                            orderRequestDTO.getUserId(),
                            orderRequestDTO.getAmount()
                    ));
                    return  new PaymentEvent(paymentRequestDTO, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse( new PaymentEvent(paymentRequestDTO, PaymentStatus.PAYMENT_FAILED));

    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        userTransactionRepositiry.findById(orderEvent.getOrderRequestDTO().getOrderId())
                .ifPresent(ut->{
                    userTransactionRepositiry.delete(ut);
                    userTransactionRepositiry.findById(ut.getUserId())
                            .ifPresent(ub->ub.setAmount(ub.getAmount()+ut.getAmount()));
                });
    }

    public List<UserBalance> getAllUseBalance(){
        return this.userBalanceRepository.findAll();
    }
}
