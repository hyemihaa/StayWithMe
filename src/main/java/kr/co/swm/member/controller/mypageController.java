package kr.co.swm.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mypageController {
    
    // 마이페이지로 이동
    @GetMapping("/mypage")
    public String mypage() {
        return "/member/mypage";
    }
}
