package kr.co.swm.reservation.controller;

import kr.co.swm.board.detail.model.DTO.DetailDTO;
import kr.co.swm.coupon.model.dto.CouponListDto;
import kr.co.swm.jwt.util.JWTUtil;
import kr.co.swm.member.model.dto.UserDTO;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.dto.WebDto;
import kr.co.swm.reservation.model.dto.PaymentDto;
import kr.co.swm.reservation.model.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;
    private final JWTUtil jwtUtil;

    @Autowired
    public ReservationController(ReservationService reservationService, JWTUtil jwtUtil) {
        this.reservationService = reservationService;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/reservation")
    public String reservation(@CookieValue(name = "Authorization", required = false)String userKey,
                              @ModelAttribute DetailDTO detailDTO,
                              Model model) {

        Long userNo = jwtUtil.getUserNoFromToken(userKey);
        int roomNo = detailDTO.getRoomNo();
        String amount = String.valueOf(detailDTO.getBoardCount());

        // 사용안한 쿠폰 리스트 전체조회
        // 최종 가격 변수 넘어오고, AND final_price < minPrice
        List<WebDto> coupons = reservationService.couponList(userNo);
        SellerDto list = reservationService.reserveList(userNo, roomNo);
        UserDTO user = reservationService.userInfo(userNo);

        // 하드코딩
        String checkIn = "2024-08-24";
        String checkOut = "2024-08-26";


        model.addAttribute("coupons", coupons);
        model.addAttribute("list", list);
        model.addAttribute("user", user);
        model.addAttribute("price", amount);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("payment", new PaymentDto());

        return "reservation";
    }

    @PostMapping("/apply-coupon")
    @ResponseBody
    public WebDto applyCoupon(@RequestBody WebDto request) {
        int couponId = request.getCouponId();

        // 서비스에서 쿠폰 ID에 따라 할인 값과 할인 타입 가져오기
        WebDto discount = reservationService.getDiscount(couponId);


        return discount;
    }

    @PostMapping("/reserve-save")
    @ResponseBody  // 이 메서드에서 반환된 객체를 JSON으로 변환하여 클라이언트에게 전달
    public ResponseEntity<Map<String, Object>> processReservation(@RequestBody PaymentDto reservationData,
                                                                  @CookieValue(name = "Authorization", required = false)String userKey
                             ) {

        Long userNo = jwtUtil.getUserNoFromToken(userKey);

        // JSON으로 넘어온 데이터를 받아옵니다.

        String reserveCheckIn = reservationData.getReserveCheckIn();
        String reserveCheckOut = reservationData.getReserveCheckOut();

        Integer couponId = reservationData.getCouponId();
        int reserveRoomNo = reservationData.getRoomNo();
        int reserveAmount = reservationData.getFinalPrice();

        // 받아온 데이터를 로그로 출력하거나 다른 로직을 수행할 수 있습니다.
        System.out.println("Check-in Date: " + reserveCheckIn);
        System.out.println("Check-out Date: " + reserveCheckOut);
        System.out.println("Final Price: " + reserveAmount);
        System.out.println("room number: " + reserveRoomNo);
        System.out.println("couponId: " + couponId);

        SellerDto sellerDto = new SellerDto(reserveCheckIn, reserveCheckOut, reserveRoomNo, reserveAmount);


        int save = reservationService.reserveSave(sellerDto, couponId, userNo);
        System.out.println("assa : " + sellerDto.getBookingNo());
        int reservationNo = sellerDto.getBookingNo();


        // 여기서 필요한 예약 처리 로직을 수행합니다.
        // 예: 데이터베이스에 저장, 추가 검증 등

        // 예약 처리 결과를 반환합니다.
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");  // 예약 처리 성공 여부를 반환
        response.put("saveNo", reservationNo);
        response.put("reserveAmount", sellerDto.getReserveAmount());

        return ResponseEntity.ok(response);  // 성공적으로 처리했음을 클라이언트에 응답
    }


    @PostMapping("/payment")
    public String payment(@ModelAttribute PaymentDto paymentDto
    ) {

        int basicPrice = paymentDto.getBasicPrice();
        int discountPrice = paymentDto.getDiscountPrice();
        int finalPrice = paymentDto.getFinalPrice();
        int reservationNo = paymentDto.getReservationNo();
        String uid = paymentDto.getUid();
        String method = paymentDto.getMethod();

        PaymentDto paymentPrices = new PaymentDto(finalPrice, basicPrice, discountPrice);

        // 결제 테이블 인입
        int reservationSave = reservationService.paymentSave(paymentPrices , reservationNo);
        int paymentNo = paymentPrices.getPaymentNo();

        // 결제 승인 테이블 인입
        PaymentDto payment = new PaymentDto(finalPrice, uid, method);
        if (reservationSave > 0) {
            reservationService.paymentDetail(payment, paymentNo);
        }


        // 예약상태 업데이트 ( 예약안료/실패 )
//        reservationService.reserveUpdate();

//        // 쿠폰 사용 시 쿠폰 use 컬럼 업데이트
          // 사용안함 쿠폰은 업데이트 안됌
//        reservationService.couponUsedUpdate();

        return "redirect:/complete";
    }

    @PostMapping("/refund")
    public ResponseEntity<String> refund(@RequestBody Map<String, String> refundData) {
        try {
            int bookingNo = Integer.parseInt(refundData.get("bookingNo"));
            String cancelBy = refundData.get("cancelBy");
            int cancelAmount = Integer.parseInt(refundData.get("cancelAmount"));

//            int refundResult = reservationService.refund(cancelBy, bookingNo, cancelAmount);
            int refundResult = reservationService.refund(cancelBy, bookingNo, cancelAmount);

            if (refundResult > 0) {
                // DB 업데이트 성공 시 OK 반환
                return ResponseEntity.ok("OK");
            } else {
                // DB 업데이트 실패 시 실패 메시지 반환
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("DB 업데이트 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 발생: " + e.getMessage());
        }
    }




    @GetMapping("/complete")
    public String complete() {
        return "complete";
    }

}
