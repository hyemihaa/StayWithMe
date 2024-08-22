package kr.co.swm.member.model.service;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.swm.member.model.dto.AdminDTO;
import kr.co.swm.member.model.dto.UserDTO;

public interface MemberService {
    // 회원가입
    public int setSignup(UserDTO userDTO);

    // 업소 관리자 가입
    int setSellerSignup(AdminDTO adminDTO);

    // 사이트 관리자 가입
    int setManagerSignup(AdminDTO adminDTO);

    // id 중복검사
    public int idCheck(String userId);

    // 인증번호 생성 메서드
    public String generateCertificationCode();

    // Cool SMS API 호출 메서드
    void sendSMSViaCoolSMS(String phoneNumber, String certificationCode);

    // 로그인
    String authenticate(String userId, String userPwd, HttpServletResponse response, String signRole);

    // 아이디 찾기
    String findUserId(String userName, String userPhone);

    // 비밀번호 찾기(임시 비밀번호 발급)
    String passwordReset(String userId, String userPhone);

    // 마이페이지 정보 불러오기
    UserDTO userInfo(String userId);

    // 마이페이지 현재 비밀번호 일치 체크
    boolean checkCurrentPassword(String userId, String currentPassword);

    // 마이페이지 새비밀번호 설정
    void updatePassword(String userId, String newPassword);

    void updatePhoneNumber(String newPhone, String userId);
}
