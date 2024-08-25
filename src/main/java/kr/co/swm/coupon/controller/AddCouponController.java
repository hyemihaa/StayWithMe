package kr.co.swm.coupon.controller;


import kr.co.swm.coupon.model.service.AddCouponService;
import kr.co.swm.coupon.model.service.AddCouponServiceImpl;
import kr.co.swm.jwt.util.JWTUtil;
import kr.co.swm.model.dto.WebDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/coupon")
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
        System.out.println("dasdaskldnlas " + id);
        List<WebDto> list = addCouponService.couponList();

        model.addAttribute("list", list);
        model.addAttribute("userNo", id);

        return "mainCoupon";
    }

    @PostMapping("/addCoupon")
    public String addCoupon(@RequestParam("couponId")int couponId,
                         @RequestParam("userNo")Long userNo) {


        System.out.println("nininininin");
        // userNo 체크
        // 인입 성공 시 해당 쿠폰 재고 업데이트
        if (userNo == null || userNo <= 0) {
            return "로그인 후에 이용 가능합니다.";
        }
        else {
           int result = addCouponService.addCoupon(couponId, userNo);
        }
        return "redirect:/couponList";
    }
}
