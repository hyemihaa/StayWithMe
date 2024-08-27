package kr.co.swm.reservation.model.service;

import kr.co.swm.coupon.model.dto.CouponListDto;
import kr.co.swm.member.model.dto.UserDTO;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.dto.WebDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReservationService {

    List<WebDto> couponList(Long userNo);

    WebDto getDiscount(int couponId);

    UserDTO userInfo(Long userNo);

    SellerDto reserveList(Long userNo);

    boolean reserveSave(String checkIn,String checkOut,  String userNo, int price);

    int paymentSave(int basicPrice, int discountPrice, int finalPrice);

}
