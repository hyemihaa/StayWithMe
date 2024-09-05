package kr.co.swm.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerDashboardDto {
    private int roomViews;
    private int reservationCount;
    private long paymentCount;
    private long cancelCount;
    private int netAmount;
}
