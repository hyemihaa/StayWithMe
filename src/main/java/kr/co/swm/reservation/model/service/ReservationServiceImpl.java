package kr.co.swm.reservation.model.service;

import kr.co.swm.coupon.model.dto.CouponListDto;
import kr.co.swm.member.model.dto.UserDTO;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.dto.WebDto;
import kr.co.swm.reservation.mapper.ReservationMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationServiceImpl(ReservationMapper reservationMapper) {
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<WebDto> couponList(Long userNo) {
        return reservationMapper.couponList(userNo);
    }

    @Override
    public SellerDto reserveList(Long userNo) {
        return reservationMapper.reserveList(userNo);
    }

    @Override
    public UserDTO userInfo(Long userNo) {
        return reservationMapper.userInfo(userNo);
    }

    @Override
    public WebDto getDiscount(int couponId) {
        return reservationMapper.getDiscount(couponId);
    }

    @Override
    public int reserveSave(SellerDto sellerDto, Integer couponId, Long userNo){
        return reservationMapper.reserveSave(sellerDto, couponId, userNo);
    }

    @Override
    public int paymentSave(int basicPrice, int discountPrice, int finalPrice, int reservationNo) {

        int result = reservationMapper.paymentSave(basicPrice, discountPrice, finalPrice, reservationNo);
        if(result == 1){
            return reservationMapper.paymentDetail();
        }
        return result;

    }

}
