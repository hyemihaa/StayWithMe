package kr.co.swm.coupon.controller;


import kr.co.swm.coupon.model.service.AddCouponService;
import kr.co.swm.coupon.model.service.AddCouponServiceImpl;
import kr.co.swm.jwt.util.JWTUtil;
import kr.co.swm.model.dto.WebDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AddCouponController {

    AddCouponServiceImpl addCouponService;
    JWTUtil jwtUtil;


    @Autowired
    public AddCouponController(AddCouponServiceImpl addCouponService, JWTUtil jwtUtil) {
        this.addCouponService = addCouponService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/couponList")
    public String couponList(@CookieValue(name = "Authorization", required = false) String userNo,
                             Model model) {


        Long id = jwtUtil.getUserNoFromToken(userNo);

        System.out.println("id: " + id);
        List<WebDto> list = addCouponService.couponList(id);

        model.addAttribute("list", list);
        model.addAttribute("userNo", id);

        return "mainCoupon";
    }

    @PostMapping("/addCoupon")
    public String addCoupon(@RequestParam("couponId")int couponId,
                         @RequestParam("userNo")Long userNo) {
        // userNo 체크
        // 인입 성공 시 해당 쿠폰 재고 업데이트
        if (userNo == null || userNo <= 0) {
            return "로그인 후에 이용 가능합니다.";
        }
        else {
           int result = addCouponService.addCoupon(couponId, userNo);
           if (result == 1) {
               addCouponService.updateCouponQuantity(couponId);
           }
        }
        return "redirect:/couponList";
    }

    @PostMapping("add-all")
    public ResponseEntity<String> addAllCoupon(@RequestParam("couponNo")List<Integer> couponIds,
                                               @RequestParam("userNo")Long userNo) {

        for (int couponId : couponIds) {
            addCouponService.addAllCoupons(couponId, userNo);
        }
        return ResponseEntity.ok("모든 쿠폰이 성공적으로 추가되었습니다.");
    }
}
