package kr.co.swm.reservation.mapper;

import kr.co.swm.board.detail.model.DTO.DetailDTO;
import kr.co.swm.member.model.dto.UserDTO;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.dto.WebDto;
import kr.co.swm.reservation.model.dto.PaymentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface ReservationMapper {

    List<WebDto> couponList(@Param("userNo")Long userNo);

    SellerDto reserveList(@Param("userNo")Long userNo, @Param("roomNo") int roomNo);

    UserDTO userInfo(@Param("userNo")Long userNo);

    WebDto getDiscount(@Param("couponId")int couponId);

    int reserveSave(@Param("sellerDto")SellerDto sellerDto, @Param("couponId")Integer couponId, @Param("userNo")Long userNo);

    int paymentSave(@Param("payment")PaymentDto paymentDto, @Param("reservationNo")int reservationNo);

    int paymentDetail(@Param("payment")PaymentDto paymentDto, @Param("paymentNo")int paymentNo);

    int refund(@Param("userNo")Long cancelBy, @Param("bookingNo")int bookingNo, @Param("cancelAmount")int cancelAmount);

    SellerDto paymentInfo(@Param("bookingNo") int bookingNo);

    void updateReservation(@Param("bookingNo") int bookingNo);

    void reserveUpdate(@Param("paymentNo") int paymentNo);

}
