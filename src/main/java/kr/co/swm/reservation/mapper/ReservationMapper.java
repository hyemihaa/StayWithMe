package kr.co.swm.reservation.mapper;

import kr.co.swm.member.model.dto.UserDTO;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.dto.WebDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationMapper {

    List<WebDto> couponList(@Param("userNo")Long userNo);

    SellerDto reserveList(@Param("userNo")Long userNo);

    UserDTO userInfo(@Param("userNo")Long userNo);

    WebDto getDiscount(@Param("couponId")int couponId);

    int reserveSave(@Param("sellerDto")SellerDto sellerDto, @Param("couponId")Integer couponId, @Param("userNo")Long userNo);

    int paymentSave(@Param("basicPrice")int basicPrice, @Param("discountPrice")int discountPrice, @Param("finalPrice")int finalPrice, @Param("reservationNo")int reservationNo);
    int paymentDetail();
}
