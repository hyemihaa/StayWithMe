package kr.co.swm.coupon.mapper;

import kr.co.swm.model.dto.WebDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddCouponMapper {
    List<WebDto> couponList();

    int addCoupon(@Param("couponId")int couponId, @Param("userNo")Long userNo);
}
