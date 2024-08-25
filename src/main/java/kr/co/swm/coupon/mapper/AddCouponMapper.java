package kr.co.swm.coupon.mapper;

import kr.co.swm.model.dto.WebDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddCouponMapper {
    List<WebDto> couponList(@Param("userId")Long userId);

    int addCoupon(@Param("couponId")int couponId, @Param("userNo")Long userNo);

    int updateCouponQuantity(@Param("couponId")int couponId);

    int addAllCoupons(@Param("couponIds")int couponIds, @Param("userNo")Long userNo);
}
