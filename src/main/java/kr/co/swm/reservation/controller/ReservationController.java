package kr.co.swm.reservation.controller;

import kr.co.swm.coupon.model.dto.CouponListDto;
import kr.co.swm.jwt.util.JWTUtil;
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
        List<WebDto> coupons = reservationService.couponList(userNo);

        model.addAttribute("coupons", coupons);

        return "reservation";
    }

    @PostMapping("/apply-coupon")
    @ResponseBody
    public WebDto applyCoupon(@RequestBody WebDto request) {
        int couponId = request.getCouponId();

        System.out.println("id " + couponId);

        // 서비스에서 쿠폰 ID에 따라 할인 값과 할인 타입 가져오기
        WebDto discount = reservationService.getDiscount(couponId);
        System.out.println("type : " + discount.getCouponType());

        return discount;
    }

}
