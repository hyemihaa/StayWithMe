package kr.co.swm.reservation.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private int paymentNo;

    private int finalPrice;
    private int basicPrice;
    private int discountPrice;

    private String uid;
    private String method;

    public PaymentDto(int finalPrice, int basicPrice, int discountPrice) {
        this.finalPrice = finalPrice;
        this.basicPrice = basicPrice;
        this.discountPrice = discountPrice;
    }

    public PaymentDto(int finalPrice, String uid, String method) {
        this.finalPrice = finalPrice;
        this.uid = uid;
        this.method = method;
    }
}
