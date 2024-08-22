package kr.co.swm.member.controller;

import kr.co.swm.jwt.util.JWTUtil;
import kr.co.swm.member.model.dto.UserDTO;
import kr.co.swm.member.model.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class mypageController {
    private final JWTUtil jwtUtil;
    private final MemberServiceImpl memberServiceImpl;

    // 마이페이지로 이동 (사용자 정보 가져오기)
    @GetMapping("/mypage")
    public String mypage(Model model, @CookieValue(value = "Authorization", required = false) String token) {
        String userId = jwtUtil.getUserIdFromToken(token); // 현재 로그인한 유저식별키
        UserDTO userDTO = memberServiceImpl.userInfo(userId);

        if (userDTO == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId);
        }
        model.addAttribute("user", userDTO);

        return "/member/mypage";
    }

    // 마이페이지 비밀번호 변경
    @PostMapping("/update-password")
    @ResponseBody
    public Map<String, Object> updatePassword(@RequestBody Map<String, String> param, @CookieValue(value = "Authorization", required = false) String token) {
        // 응답을 담을 Map 객체 생성
        Map<String, Object> response = new HashMap<>();
        try {
            // param에서 currentPassword와 newPassword 키의 값 추출
            String currentPassword = param.get("currentPassword");
            String newPassword = param.get("newPassword");
            System.out.println("사용자가 입력한 새로운 비밀번호 : " + newPassword);

            // 사용자 식별
            String userId = jwtUtil.getUserIdFromToken(token);

            // 현재 비밀번호 확인
            if (!memberServiceImpl.checkCurrentPassword(userId, currentPassword)) {
                response.put("success", false);
                response.put("error", "현재 비밀번호가 일치하지 않습니다.");
                return response;
            }
            // 새 비밀번호 설정
            memberServiceImpl.updatePassword(userId, newPassword);

            // 성공 응답
            response.put("success", true);
        } catch (Exception e) {
            // 실패 응답
            response.put("success", false);
            response.put("error", "비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
        }
        // 응답 결과 반환
        return response;
    }

    // 마이페이지 휴대전화 번호 업데이트
    @PostMapping("/update-phone")
    @ResponseBody
    public Map<String, Object> updatePhone(@RequestBody Map<java.lang.String, java.lang.String> param, @CookieValue(value = "Authorization", required = false) String token) {
        // 응답을 담을 Map 객체 생성
        Map<String, Object> response = new HashMap<>();
        String newPhone = param.get("newPhone");
        System.out.println("새로운 휴대전화 번호 : " + newPhone);

        // 사용자 식별
        String userId = jwtUtil.getUserIdFromToken(token);

        // 새로운 휴대전화 번호 업데이트
        try {
            memberServiceImpl.updatePhoneNumber(newPhone,userId);
            // 성공 응답
            response.put("success", true);
            response.put("message", "휴대전화 번호가 성공적으로 변경되었습니다.");
        }
        catch (Exception e) {
            // 오류 발생 시
            response.put("success", false);
            response.put("error", "휴대전화 번호 변경 중 오류가 발생했습니다.");
        }
        return response;
    }

}
