package event;

import dto.OrderRequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderEvent implements Event {
    private UUID eventID = UUID.randomUUID();
    private Date date = new Date();
    private OrderRequestDTO orderRequestDTO;
    private OrderStatus orderStatus;

    public OrderEvent(OrderRequestDTO orderRequestDTO, OrderStatus orderStatus) {
        this.orderRequestDTO = orderRequestDTO;
        this.orderStatus = orderStatus;
    }

    @Override
    public UUID getEventId() {
        return eventID;
    }

    @Override
    public Date getDate() {
        return date;
    }
}
