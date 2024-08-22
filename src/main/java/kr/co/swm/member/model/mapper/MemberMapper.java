package kr.co.swm.member.model.mapper;

import kr.co.swm.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    MemberDTO findByUserId(@Param("userId") String userId);

    int setSignUp(@Param("memberDTO") MemberDTO memberDTO);

    int idCheck(@Param("userId") String userId);

    MemberDTO accommodationAdminByUserId(@Param("userId") String userId);

    String findUserId(@Param("userName") String userName, @Param("userPhone") String userPhone);

    String verifyUser(@Param("userId") String userId, @Param("userPhone") String userPhone);

    void updateResetPassword(@Param("userId") String userId, @Param("userPhone") String userPhone, @Param("encodedPassword") String encodedPassword);
}
