package kr.co.swm.controller;

import kr.co.swm.model.service.WebServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebManagerController {

    private final WebServiceImpl web;

    @Autowired
    public WebManagerController(WebServiceImpl web) {
        this.web = web;
    }

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-login")
    public String webCenterLogin() {
        return "web_manager/webcenter_login";
    }




//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-center")
    public String webCenterDashboard(Model model) {


        return "web_manager/webcenter_dashboard";
    }


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-coupon")
    public String webCenterCoupon(Model model) {


        return "web_manager/webcenter_coupon";
    }



//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-member")
    public String webCenterMember(Model model) {


        return "web_manager/webcenter_member_search";
    }


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-seller")
    public String webCenterSeller(Model model) {


        return "web_manager/webcenter_seller";
    }


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-manager")
    public String webCenterManager(Model model) {


        return "web_manager/webcenter_manager";
    }
}
