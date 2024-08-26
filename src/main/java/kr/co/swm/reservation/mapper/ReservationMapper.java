package kr.co.swm.reservation.mapper;

import kr.co.swm.coupon.model.dto.CouponListDto;
import kr.co.swm.model.dto.WebDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationMapper {

    List<WebDto> couponList(@Param("userNo")Long userNo);

    WebDto getDiscount(@Param("couponId")int couponId);
}
