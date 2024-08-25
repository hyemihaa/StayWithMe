package kr.co.swm.coupon.model.service;

import kr.co.swm.model.dto.WebDto;

import java.util.List;

public interface AddCouponService {
    List<WebDto> couponList();

    int addCoupon(int couponId, Long userNo);
}
