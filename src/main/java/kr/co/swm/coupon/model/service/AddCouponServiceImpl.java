package kr.co.swm.coupon.model.service;

import kr.co.swm.coupon.mapper.AddCouponMapper;
import kr.co.swm.model.dto.WebDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddCouponServiceImpl implements AddCouponService {

    AddCouponMapper addCouponMapper;

    @Autowired
    public AddCouponServiceImpl(AddCouponMapper addCouponMapper) {
        this.addCouponMapper = addCouponMapper;
    }


    @Override
    public List<WebDto> couponList(Long userId) {
        List<WebDto> list = addCouponMapper.couponList(userId);
        return list;
    }

    @Override
    public int addCoupon(int couponId, Long userId) {

        return addCouponMapper.addCoupon(couponId, userId);
    }

    @Override
    public int updateCouponQuantity(int couponId) {
        return addCouponMapper.updateCouponQuantity(couponId);
    }

    @Override
    public int addAllCoupons(int couponIds, Long userNo){
        return addCouponMapper.addAllCoupons(couponIds, userNo);
    }

}
