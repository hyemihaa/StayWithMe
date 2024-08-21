package kr.co.swm.member.controller;

import jakarta.validation.Valid;
import kr.co.swm.member.model.dto.MemberDTO;
import kr.co.swm.member.model.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor //final로 선언된 필드가 있다면, 이 필드들을 초기화하는 생성자를 자동으로 생성
@Controller
public class MemberController {

    private final MemberService memberService;

    // 회원가입(Form) 페이지 이동
    @GetMapping("/signForm")
    public String showSignForm(Model model) {
        // th:object로 바인딩되어 폼 필드와 연결
        model.addAttribute("memberDTO", new MemberDTO());
        return "member/sign";
    }

    // 회원가입 시 휴대전화 인증
    @PostMapping("/sms/send")
    @ResponseBody
    public Map<String, Object> sendSMS(@RequestBody Map<String, String> param) {
        // 응답을 담을 Map 객체 생성
        Map<String, Object> response = new HashMap<>();
        try {
            // param에서 userPhone 키의 값 추출, 휴대전화 번호 저장
            String phoneNumber = param.get("phoneNumber");

            // 인증번호 생성 요청
            String certificationCode = memberService.generateCertificationCode();
            // 생성된 인증번호 -> sms 전송
            memberService.sendSMSViaCoolSMS(phoneNumber, certificationCode);

            // 성공 응답
            response.put("success", true);
            response.put("certificationCode", certificationCode);
        } catch (Exception e) {
            // 실패 응답
            response.put("success", false);
            response.put("error", "인증번호 발송에 실패했습니다. 다시 시도해주세요.");
        }
        // 응답 결과 반환
        return response;
    }

    // 아이디 중복 체크
    @PostMapping("/idCheck")
    @ResponseBody
    public Map<String, Object> idCheck(@RequestBody Map<String, String> param) {
        // 응답을 담을 Map 객체 생성
        Map<String, Object> response = new HashMap<>();

        String userId = param.get("userId");
        // 아이디 체크
        int result = memberService.idCheck(userId);
        // 중복
        response.put("result", result);
        // 응답 결과 반환
        return response;
    }

    // 회원가입
    @PostMapping("/signUp")
    public String signUp(@Valid MemberDTO memberDTO) {
        System.out.println(memberDTO.getUserId());
        System.out.println(memberDTO.getUserPwd());
        System.out.println(memberDTO.getUserName());
        int result = memberService.setSignup(memberDTO);
        return "member/sign";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "member/mypage";
    }

}
