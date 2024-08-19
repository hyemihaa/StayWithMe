package kr.co.swm.member.model.service;

import kr.co.swm.member.model.mapper.MemberMapper;
import kr.co.swm.member.util.SmsCertificationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

class MemberServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MemberMapper memberMapper;

    @Mock
    private SmsCertificationUtil smsCertificationUtil;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendSMSViaCoolSMS() {
        // Arrange
        String phoneNumber = "01012345678";
        String certificationCode = "123456";

        // Act
        memberService.sendSMSViaCoolSMS(phoneNumber, certificationCode);

        // Assert
        verify(smsCertificationUtil, times(1)).sendSMS(phoneNumber, certificationCode);
    }
}
