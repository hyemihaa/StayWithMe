package kr.co.swm.member.model.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.swm.config.auth.CustomUserDetails;
import kr.co.swm.config.auth.CustomUserDetailsService;
import kr.co.swm.jwt.util.JWTUtil;
import kr.co.swm.member.model.dto.MemberDTO;
import kr.co.swm.member.model.mapper.MemberMapper;
import kr.co.swm.member.util.PasswordUtils;
import kr.co.swm.member.util.SmsCertificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;
    private final SmsCertificationUtil smsCertificationUtill;
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTUtil jwtUtil;

    // 회원가입
    public int setSignup(MemberDTO memberDTO) {
        String userPwd = memberDTO.getUserPwd();
        String userConfirmPwd = memberDTO.getConfirmPassword();

        if(userPwd.equals(userConfirmPwd)) {
            // 비밀번호와 비밀번호 확인이 일치하는지 체크
            String encodedPassword = passwordEncoder.encode(userPwd);
            System.out.println("encodedPassword : " + encodedPassword);
            // 암호화 비밀번호 DTO에 저장
            memberDTO.setUserPwd(encodedPassword);
            // 현재 날짜와 시간 설정
            memberDTO.setCreatedDate(LocalDateTime.now());
            System.out.println("memberDTO : " + memberDTO.getUserPwd());

            return memberMapper.setSignUp(memberDTO);
        }
        else {
            System.out.println("else");
            return 0;
        }
    }

    public int setSellerSignup(MemberDTO memberDTO) {
        String userPwd = memberDTO.getUserPwd();

            // 비밀번호와 비밀번호 확인이 일치하는지 체크
            String encodedPassword = passwordEncoder.encode(userPwd);
            // 암호화 비밀번호 DTO에 저장
            memberDTO.setUserPwd(encodedPassword);

            return memberMapper.setSellerSignup(memberDTO);
    }

    public int setManagerSignup(MemberDTO memberDTO) {
        String userPwd = memberDTO.getUserPwd();

        // 비밀번호와 비밀번호 확인이 일치하는지 체크
        String encodedPassword = passwordEncoder.encode(userPwd);
        // 암호화 비밀번호 DTO에 저장
        memberDTO.setUserPwd(encodedPassword);

        return memberMapper.setManagerSignup(memberDTO);
    }

    // id 중복검사
    public int idCheck(String userId) {
        return memberMapper.idCheck(userId);
    }

    // 인증번호 생성 메서드
    public String generateCertificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    // Cool SMS API 호출 메서드
    public void sendSMSViaCoolSMS(String phoneNumber, String certificationCode) {
        smsCertificationUtill.sendSMS(phoneNumber, certificationCode); // SmsCertificationUtil을 사용하여 SMS 발송
    }

    // 로그인
    public String authenticate(String userId, String userPwd, HttpServletResponse response) {
        // 사용자 정보 로드 및 권한 초기화
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);
        String role = "ROLE_USER"; // 기본 권한 (기본값은 일반 사용자)
        Long accommAdminKey = null; // 숙소 관리자 키 초기화

        // 사용자 정보가 존재하는 경우 (일반 유저, 사이트 관리자 또는 숙소 관리자)
        if (userDetails != null) {
            // 권한을 확인하여 부여
            role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("ROLE_USER");
        }
        if ("ROLE_ACCOMMODATION_ADMIN".equals(role)) {
                if (userDetails instanceof CustomUserDetails) {
                    accommAdminKey = ((CustomUserDetails) userDetails).getMemberDTO().getAccommodationAdminNo();
                }
            role = "ROLE_ACCOMMODATION_ADMIN";
        }

        System.out.println("========memberService========");

        // JWT 토큰 생성 및 쿠키 저장
        if (userDetails != null && passwordEncoder.matches(userPwd, userDetails.getPassword())) {
            // JWT에 포함할 클레임(정보) 생성
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", userId);
            claims.put("role", role);

            // 숙소 관리자일 경우 숙소 키 추가
            if ("ROLE_ACCOMMODATION_ADMIN".equals(role)) {
                claims.put("accommAdminKey", accommAdminKey);
            }

            // claims에 추가된 값을 로그로 출력하여 확인
            System.out.println("Claims 내용: " + claims);

            System.out.println("------------memberService : " + accommAdminKey +"------------------");

            // 토큰생성
            LocalDateTime expireAt = LocalDateTime.now().plusHours(1);
            String token = jwtUtil.create(claims, expireAt, accommAdminKey);

            // 토큰 쿠키에 저장
            Cookie accessCookie = new Cookie("Authorization", token);
            accessCookie.setMaxAge(60*60); // 1시간 동안 유효
            accessCookie.setPath("/"); // 모든 경로에 대해 쿠키 전송
            accessCookie.setDomain("localhost"); // 도메인 설정
            accessCookie.setSecure(false);
            response.addCookie(accessCookie);

            return token;
        }

        // 인증 실패 시 null 반환
        return null;
    }

    // 아이디 찾기
    public String findUserId(String userName, String userPhone) {
        return memberMapper.findUserId(userName, userPhone);
    }

    // 비밀번호 찾기(임시 비밀번호 발급)
    public String passwordReset(String userId, String userPhone) {
        // 사용자검증
        String userExists =  memberMapper.verifyUser(userId, userPhone);
        if(userExists == null) {
            return "error: 해당 정보를 가진 사용자를 찾을 수 없습니다.";
        }

        // 사용자검증 성공 -> 임시비밀번호 생성
        String changePassword = PasswordUtils.generateTemporaryPassword();
        System.out.println("임시비밀번호 생성" + changePassword);

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(changePassword);
        memberMapper.updateResetPassword(userId, userPhone, encodedPassword);

        return changePassword;
    }
}
