package kr.co.swm.reservation.model.service;

import kr.co.swm.board.detail.model.DTO.DetailDTO;
import kr.co.swm.coupon.model.dto.CouponListDto;
import kr.co.swm.member.model.dto.UserDTO;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.dto.WebDto;
import kr.co.swm.reservation.model.dto.PaymentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReservationService {

    List<WebDto> couponList(Long userNo);

    WebDto getDiscount(int couponId);

    UserDTO userInfo(Long userNo);

    SellerDto reserveList(Long userNo, int roomNo);

    int reserveSave(SellerDto sellerDto, Integer couponId, Long userNo);

    int paymentSave(PaymentDto paymentDto, int reservationNo);

    int paymentDetail(PaymentDto paymentDto, int paymentNo);

    int refund(Long cancelBy, int bookingNo,int  cancelAmount);

    SellerDto paymentInfo(int bookingNo);

    void updateReservation(int bookingNo);
}
