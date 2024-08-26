package kr.co.swm.reservation.model.service;

import kr.co.swm.coupon.model.dto.CouponListDto;
import kr.co.swm.model.dto.WebDto;

import java.util.List;

public interface ReservationService {

    List<WebDto> couponList(Long userNo);

    WebDto getDiscount(int couponId);
}
