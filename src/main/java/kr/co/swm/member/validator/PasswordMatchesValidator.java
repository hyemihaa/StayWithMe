package kr.co.swm.member.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.co.swm.member.model.dto.MemberDTO;

public class PasswordMatchesValidator
                           /* ConstraintValidator : 특정 어노테이션에 대한 유효성 검사를 수행
                                 PasswordMatches : 검사할 어노테이션 타입
                                 MemberDTO : 유효성 검사를 수행할 대상의 타입 */
        implements ConstraintValidator<PasswordMatches, MemberDTO> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        // 초기화 메서드

    }

    @Override
    public boolean isValid(MemberDTO memberDTO, ConstraintValidatorContext constraintValidatorContext) {
        // 비밀번호, 비밀번호 확인 필드가 null이 아닌지 확인
        if (memberDTO.getUserPwd() == null || memberDTO.getConfirmPassword() == null) {
            return false;
        }

        // 두 비밀번호가 일치하는지 검사
        boolean isValid = memberDTO.getUserPwd().equals(memberDTO.getConfirmPassword());

        if (!isValid) {
            // 기본 에러 메세지 비활성화
            constraintValidatorContext.disableDefaultConstraintViolation();

            // 에러 메세지 설정
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("비밀번호가 일치하지 않습니다.")
                    .addConstraintViolation();
        }
        return isValid;  // isValid 값 반환 (일치 true, 불일치 false)
    }

}
