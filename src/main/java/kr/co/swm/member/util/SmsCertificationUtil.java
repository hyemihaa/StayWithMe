package kr.co.swm.member.util;

import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsCertificationUtil {
    @Value("${coolsms.apikey}") // coolsms api 키
    private String apiKey;

    @Value("${coolsms.api-secret}") // coolsms api 비밀키
    private String apiSecret;

    @Value("${coolsms.from-number}") // 발신자번호
    private String fromNumber;

    @Value("${coolsms.domain}")
    private String domain;

    private DefaultMessageService messageService;

    @PostConstruct
    public void init() {
        // Cool SMS 메시지 서비스 초기화
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, domain);
    }

    // SMS 발송 메서드
    public void sendSMS(String to, String certificationCode) {

        Message message = new Message();
        message.setFrom(fromNumber); // 발신자 번호 설정
        message.setTo(to); // 수신자 번호 설정
        message.setText("본인확인 인증번호는 " + certificationCode + "입니다."); // 메시지 내용 설정

        // 메시지 발송 요청
        this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }
}
