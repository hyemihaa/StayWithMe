package kr.co.swm.coupon.mapper;

import kr.co.swm.model.dto.WebDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddCouponMapper {
    List<WebDto> couponList();
}
