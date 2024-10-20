package event;

import dto.PaymentRequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PaymentEvent  implements Event{

    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private PaymentRequestDTO paymentRequestDTO;
    private PaymentStatus paymentStatus;

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }

    public PaymentEvent(PaymentRequestDTO paymentRequestDTO, PaymentStatus paymentStatus) {
        this.paymentRequestDTO = paymentRequestDTO;
        this.paymentStatus = paymentStatus;
    }
}
