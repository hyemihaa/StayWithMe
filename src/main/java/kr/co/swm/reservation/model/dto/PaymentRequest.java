package kr.co.swm.reservation.model.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequest {

    private String imp_uid;
    private String merchant_uid;
    private String transaction_id;


    @JsonCreator
    public PaymentRequest(@JsonProperty("imp_uid")String imp_uid,
                          @JsonProperty("merchant_uid")String merchant_uid,
                          @JsonProperty("transaction_id")String transaction_id
    ) {
        this.imp_uid = imp_uid;
        this.merchant_uid = merchant_uid;
        this.transaction_id = transaction_id;
    }
}
