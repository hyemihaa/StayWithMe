package kr.co.swm.member.model.service;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.swm.adminPage.accommodation.model.dto.AccommodationImageDto;
import kr.co.swm.member.model.dto.AdminDTO;
import kr.co.swm.member.model.dto.UserDTO;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.dto.WebDto;

import java.util.List;

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

    // 마이페이지 휴대전화 번호 변경
    void updatePhoneNumber(String newPhone, String userId);

    // 로그기록 저장
    void saveLoginLog(UserDTO userDTO);

    // 로그 기록 조회
    List<UserDTO> loginLog(Long userNo);

    // 회원 탈퇴
    void updateUserStatus(String userId, String status, String withdrawalReason);

    // 사용자가 받은 쿠폰
    List<WebDto> getUserCoupons(Long userNo);

    // 사용자가 예약한 내역
    List<SellerDto> getUserReservation(Long userNo);
}
