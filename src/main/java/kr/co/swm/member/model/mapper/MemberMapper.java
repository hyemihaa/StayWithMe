package kr.co.swm.member.model.mapper;

import kr.co.swm.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    // 회원가입
    public int setSignUp(@Param("memberDTO") MemberDTO memberDTO);

    // 중복 아이디 체크
    public int idCheck(@Param("userId") String userId);

    // 로그인한 회원의 전제정보
    public MemberDTO findByUserInfo(@Param("userId") String userId);

    // 로그인한 업소 관리자
    public MemberDTO findByAccommAdminInfo(@Param("userId") String userId);

    // 로그인한 사이트 관리자
    MemberDTO findBySiteAdminInfo(@Param("userId") String userId);

    // 아이디 찾기
    public String findUserId(@Param("userName") String userName, @Param("userPhone") String userPhone);

    // 비밀번호 찾기에서 사용자 검증
    public String verifyUser(@Param("userId") String userId, @Param("userPhone") String userPhone);

    // 임시비밀번호 암호화 update
    public void updateResetPassword(@Param("userId") String userId, @Param("userPhone") String userPhone, @Param("encodedPassword") String encodedPassword);

    int setSellerSignup(@Param("memberDTO") MemberDTO memberDTO);

    int setManagerSignup(@Param("memberDTO") MemberDTO memberDTO);
}
