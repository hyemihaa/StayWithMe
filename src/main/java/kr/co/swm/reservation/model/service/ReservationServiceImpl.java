package kr.co.swm.reservation.model.service;

import kr.co.swm.coupon.model.dto.CouponListDto;
import kr.co.swm.model.dto.WebDto;
import kr.co.swm.reservation.mapper.ReservationMapper;
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

    public List<WebDto> couponList(Long userNo) {
        return reservationMapper.couponList(userNo);
    }

    public WebDto getDiscount(int couponId) {
        return reservationMapper.getDiscount(couponId);
    }
}
