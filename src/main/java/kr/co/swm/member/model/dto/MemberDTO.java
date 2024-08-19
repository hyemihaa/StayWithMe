package kr.co.swm.member.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kr.co.swm.member.validator.PasswordMatches;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@PasswordMatches
public class MemberDTO {
    private Long userNo;

    //@NotBlank : null X, 공백이 아닌 문자 포함
    @NotBlank(message =  "아이디는 필수 입력값입니다")
    @Pattern(regexp = "^(?=.*[a-z])[a-z0-9]{6,20}$", message = "영문 소문자 + 숫자 6 ~ 20자리여야 합니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$", message = "영문+숫자+특수문자를 포함하여 8~20자로 입력하세요.")
    private String userPwd;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String confirmPassword;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Pattern(regexp = "^[가-힣]{2,10}$", message = "한글만 가능 합니다.")
    private String userName;

    @NotBlank(message = "전화번호는 필수 입력값입니다")
    @Pattern(regexp = "^010\\d{8}$", message = "'-' 없이 입력하세요.")
    private String userPhone;

    private String role;

    private LocalDateTime createdDate; // 생성일
    private LocalDateTime deletedDate; // 탈퇴일

    // 업소 관리자 테이블의 필드들
    private Long accommodationAdminNo;  // 업소 관리자 번호 (Primary Key)
    private String adminType; // 업소관리자의 권한



}
