package kr.co.swm.member.model.dto;

import kr.co.swm.member.validator.PasswordMatches;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches
public class AdminDTO extends MemberDTO{
    // 업소 관리자 테이블의 필드들
    private Long accommodationAdminNo;  // 업소 관리자 번호 (Primary Key)
}
