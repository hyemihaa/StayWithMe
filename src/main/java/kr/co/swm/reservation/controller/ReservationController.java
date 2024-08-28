package kr.co.swm.reservation.controller;

import kr.co.swm.coupon.model.dto.CouponListDto;
import kr.co.swm.jwt.util.JWTUtil;
import kr.co.swm.member.model.dto.UserDTO;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.dto.WebDto;
import kr.co.swm.reservation.model.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final JWTUtil jwtUtil;

    @Autowired
    public ReservationController(ReservationService reservationService, JWTUtil jwtUtil) {
        this.reservationService = reservationService;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/reservation")
    public String reservation(@CookieValue(name = "Authorization", required = false)String userKey,
                              Model model) {

        Long userNo = jwtUtil.getUserNoFromToken(userKey);

        System.out.println("userNo : " + userNo);

        // 사용안한 쿠폰 리스트 전체조회
        // 최종 가격 변수 넘어오고, AND final_price < minPrice
        List<WebDto> coupons = reservationService.couponList(userNo);
        SellerDto list = reservationService.reserveList(userNo);
        UserDTO user = reservationService.userInfo(userNo);


        // 하드코딩
        int price = 200;
        String checkIn = "2024-08-24";
        String checkOut = "2024-08-26";


        model.addAttribute("coupons", coupons);
        model.addAttribute("list", list);
        model.addAttribute("user", user);
        model.addAttribute("price", price);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);

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
    public ResponseEntity<Map<String, Object>> processReservation(@RequestBody Map<String, Object> reservationData,
                                                                  @CookieValue(name = "Authorization", required = false)String userKey
                             ) {

        Long userNo = jwtUtil.getUserNoFromToken(userKey);

        // JSON으로 넘어온 데이터를 받아옵니다.
        String reserveCheckIn = (String) reservationData.get("checkInDate");
        String reserveCheckOut = (String) reservationData.get("checkOutDate");


        Integer reserveAmount = (Integer) reservationData.get("finalPrice");

        Integer couponId = null;
        if (reservationData.get("selectedCouponId") != null) {
            couponId = Integer.parseInt((String) reservationData.get("selectedCouponId"));
        }

        Integer reserveRoomNo = null;
        if (reservationData.get("roomNo") != null) {
            reserveRoomNo = Integer.parseInt((String) reservationData.get("roomNo"));
        }


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
    @ResponseBody
    public ResponseEntity<?> payment(@RequestParam("basic-price")String basicPriceStr,
                                     @RequestParam("discount-price")String discountPriceStr,
                                     @RequestParam("reserveAmount")String finalPriceStr,
                                     @RequestParam("reNo") String reservationNoStr,
                                     @RequestParam("tid")String tidStr
    ) {

        int basicPrice = Integer.parseInt(basicPriceStr);
        int discountPrice = Integer.parseInt(discountPriceStr);
        int finalPrice = Integer.parseInt(finalPriceStr);
        int reservationNo = Integer.parseInt(reservationNoStr);
        int tid = Integer.parseInt(tidStr);

        System.out.println("BPrice : " + basicPrice);
        System.out.println("DPrice : " + discountPrice);
        System.out.println("FPrice : " + finalPrice);
        System.out.println("bookingNo : " + reservationNo);
        System.out.println("tid : " + tid);


        // 결제 테이블 인입
        int reservationSave = reservationService.paymentSave(basicPrice, discountPrice, finalPrice, reservationNo);

        // 결제 승인 테이블 인입


        // 예약상태 업데이트 ( 예약안료/실패 )
//        reservationService.reserveUpdate();

//        // 쿠폰 사용 시 쿠폰 use 컬럼 업데이트
          // 사용안함 쿠폰은 업데이트 안됌
//        reservationService.couponUsedUpdate();

        return ResponseEntity.ok(true);
    }
}
