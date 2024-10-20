package dto;

import event.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Integer userId;
    private Integer productId;
    private Integer amount;
    private Integer orderId;
    private OrderStatus orderStatus;
}
