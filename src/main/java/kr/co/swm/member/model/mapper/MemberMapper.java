package kr.co.swm.member.model.mapper;

import kr.co.swm.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    public MemberDTO findByUserId(@Param("userId") String userId);

    public int setSignUp(@Param("memberDTO") MemberDTO memberDTO);

    public int idCheck(@Param("userId") String userId);

    public MemberDTO accommodationAdminByUserId(@Param("userId") String userId);

    public String findUserId(@Param("userName") String userName, @Param("userPhone") String userPhone);

    public String verifyUser(@Param("userId") String userId, @Param("userPhone") String userPhone);

    public void updateResetPassword(@Param("userId") String userId, @Param("userPhone") String userPhone, @Param("encodedPassword") String encodedPassword);
}
