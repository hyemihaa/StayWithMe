package kr.co.swm.coupon.model.service;

import kr.co.swm.model.dto.WebDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddCouponService {
    List<WebDto> couponList(Long userId);

    int addCoupon(int couponId, Long userNo);

    int updateCouponQuantity(int couponId);

    int addAllCoupons(int couponIds, Long userId);
}
