package kr.co.swm.reservation.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVerificationResponse {

    private PaymentData response;

    @Getter
    @Setter
    public static class PaymentData {
        @JsonProperty("imp_uid")
        private String imp_uid;
        @JsonProperty("merchant_uid")
        private String merchant_uid;
        @JsonProperty("status")
        private String status;
        @JsonProperty("apply_num")
        private String apply_num;
    }
}
