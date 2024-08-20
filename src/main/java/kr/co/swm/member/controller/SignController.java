package kr.co.swm.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.co.swm.jwt.util.JWTUtil;
import kr.co.swm.member.model.dto.MemberDTO;
import kr.co.swm.member.model.service.MemberService;
import kr.co.swm.member.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor //final로 선언된 필드가 있다면, 이 필드들을 초기화하는 생성자를 자동으로 생성
@Controller
public class SignController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    // 회원가입 & 로그인 페이지 이동
    @GetMapping("/signform")
    public String showSignForm(Model model) {
        // th:object로 바인딩되어 폼 필드와 연결
        model.addAttribute("signUp", new MemberDTO());
        model.addAttribute("signIn", new MemberDTO());
        return "member/sign";
    }

    // 휴대전화 인증
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
    @PostMapping("/idcheck")
    @ResponseBody
    public Map<String, Object> idCheck(@RequestBody Map<String, String> param) {
        // 응답을 담을 Map 객체 생성
        Map<String, Object> response = new HashMap<>();

        String userId = param.get("userId");
        // 아이디 체크
        int result = memberService.idCheck(userId);
        response.put("result", result);
        // 응답 결과 반환
        return response;
    }

    // 회원가입
    @PostMapping("/signup")
    public String signUp(@Valid MemberDTO memberDTO, BindingResult bindingResult) {
        // bindingResult -> model.Attribute 하지 않아도 Model 객체에 자동 바인딩
        if (bindingResult.hasErrors()) {  // 유효성 검사에서 발생한 에러확인 -> 있을시 true, 없을시 false
            return "member/sign";
        }

        int result = memberService.setSignup(memberDTO);
        return "redirect:/signform";
    }

    // 첫 로그인 요청
    @PostMapping("/signin")
    public String signIn(@RequestParam(value = "userId") String userId, @RequestParam(value = "userPwd") String userPwd,
                         RedirectAttributes redirectAttributes, HttpServletResponse response) {
        // 사용자 로그인 호출
        String token = memberService.authenticate(userId, userPwd, response);

        if (token != null) {
            String role = jwtUtil.getRoleFromToken(token);

            System.out.println("로그인 시도: " + role);  // 권한이 제대로 추출되는지 확인

            // 권한에 따라 리다이렉트할 페이지 결정
            if ("ROLE_SITE_ADMIN".equals(role)) {
                return "redirect:/site-admin/dashboard"; // 사이트 관리자 페이지로 리다이렉트

            } else if ("ROLE_ACCOMMODATION_ADMIN".equals(role)) {
                return "redirect:/"; // 업소 관리자 페이지로 리다이렉트 ( 추후수정 )
            } else {
                return "redirect:/"; // 일반 사용자 -> 메인
            }
        } else {
            // 인증 실패: 로그인 페이지로 다시 이동하고 에러 메시지 전달
            redirectAttributes.addFlashAttribute("error", "아이디 또는 비밀번호가 틀립니다.");
            return "redirect:/signform";
        }
    }

    // 로그아웃 메세지
    @GetMapping("/logout-success")
    public String logoutSuccess(Model model) {
        model.addAttribute("logoutMessage", "로그아웃 되었습니다.");
        return "/index";
    }

    // 아이디 찾기 & 비밀번호 찾기 페이지 이동
    @GetMapping("/lostpass")
    public String lostpass() {
        return "member/find-id-password";
    }

    // 아이디 찾기
    @PostMapping("/find-userId")
    @ResponseBody
    public String findUserId(@RequestParam("userName") String userName, @RequestParam("userPhone") String userPhone) {
        System.out.println("아이디찾기 컨트롤러 : " + userName + " " + userPhone);

        // 사용자 아이디를 찾는 서비스 호출
        String userId = memberService.findUserId(userName, userPhone);
        System.out.println("========================" + userId);

        if (userId != null) {
            return userId;
        } else {
            return "error: 해당 정보를 가진 사용자를 찾을 수 없습니다.";
        }
    }

    // 비밀번호 찾기 (임시비밀번호 발급)
    @PostMapping("/find-password")
    @ResponseBody
    public String findPassword(@RequestParam("userId") String userId, @RequestParam("userPhone") String userPhone) {
        try {
            // 비밀번호 재설정 서비스 호출
            String changePassword = memberService.passwordReset(userId, userPhone);

            // 임시 비밀번호 반환 (에러 메시지일 수 있음)
            return changePassword;
        } catch (Exception e) {
            // 예외 발생 시 에러 메시지 반환
            return "error: 임시 비밀번호 발급 중 오류가 발생했습니다. 다시 시도해주세요.";
        }
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "/member/mypage";
    }
}



