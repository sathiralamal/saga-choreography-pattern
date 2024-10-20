package org.example.paymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class UserBalance {
    @Id
    private Integer userId;
    private Integer price;
}
