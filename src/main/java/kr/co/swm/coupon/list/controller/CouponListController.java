package kr.co.swm.coupon.list.controller;


import kr.co.swm.coupon.list.model.dto.CouponDto;
import kr.co.swm.coupon.list.model.service.CouponListService;
import kr.co.swm.coupon.list.model.service.CouponListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CouponListController {

    private final CouponListServiceImpl coupon;

    @Autowired
    public CouponListController(CouponListServiceImpl coupon) {
        this.coupon = coupon;
    }

    @GetMapping("/coupon")
    public String coupon(Model model) {
//        List<CouponDto> list = coupon.selectAll
        return "mainCoupon";
    }

}
