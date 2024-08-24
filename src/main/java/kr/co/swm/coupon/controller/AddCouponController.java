package kr.co.swm.coupon.controller;


import kr.co.swm.coupon.model.service.AddCouponService;
import kr.co.swm.model.dto.WebDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/coupon")
public class AddCouponController {

    AddCouponService addCouponService;


    @Autowired
    public AddCouponController(AddCouponService addCouponService) {
        this.addCouponService = addCouponService;
    }

    @GetMapping("/list")
    public String couponList(Model model, @CookieValue(name = "userNo") int userNo) {
        List<WebDto> list = addCouponService.couponList();

        model.addAttribute("list", list);
        model.addAttribute("userId", userNo);

        return "mainCoupon";
    }

    @PostMapping("/add")
    public int addCoupon() {
        return 0;
    }
}
