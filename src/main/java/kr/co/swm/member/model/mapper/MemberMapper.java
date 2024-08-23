package kr.co.swm.member.model.mapper;

import kr.co.swm.member.model.dto.AdminDTO;
import kr.co.swm.member.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    // 회원가입
    int setSignUp(@Param("userDTO") UserDTO userDTO);

    // 중복 아이디 체크
    int idCheck(@Param("userId") String userId);

    // 로그인한 회원의 전제정보
    UserDTO findByUserInfo(@Param("userId") String userId);

    // 로그인한 업소 관리자
    AdminDTO findByAccommAdminInfo(@Param("userId") String userId);

    // 로그인한 사이트 관리자
    AdminDTO findBySiteAdminInfo(@Param("userId") String userId);

    // 아이디 찾기
    String findUserId(@Param("userName") String userName, @Param("userPhone") String userPhone);

    // 비밀번호 찾기에서 사용자 검증
    String verifyUser(@Param("userId") String userId, @Param("userPhone") String userPhone);

    // 임시비밀번호 암호화 update
    void updateResetPassword(@Param("userId") String userId, @Param("userPhone") String userPhone, @Param("encodedPassword") String encodedPassword);

    int setSellerSignup(@Param("adminDTO") AdminDTO adminDTO);

    int setManagerSignup(@Param("adminDTO") AdminDTO adminDTO);

    // 마이페이지 비밀번호 변경
    void updatePassword(@Param("userDTO") UserDTO userDTO);

    // 마이페이지 휴대전화 번호 변경
    void updatePhoneNumber(@Param("newPhone") String newPhone, @Param("userId") String userId);

    // 로그인 로그 기록 저장
    void insertLoginLog(@Param("userDTO") UserDTO userDTO);

    // 로그 조회
    List<UserDTO> getUserLogsByUserNo(@Param("no") Long no);
}
