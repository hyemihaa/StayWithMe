package kr.co.swm.coupon.list.model.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponDto {

    private int couponNo;
    private String couponName;
    private int couponQuantity;
    private int useQuantity;
    private int leftOver;
    private LocalDateTime couponStart;
    private LocalDateTime couponEnd;
    private int discount;
    private String couponType;
    private int minPurchasePrice;

    private String couponUseYN;


}
