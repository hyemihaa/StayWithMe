//package kr.co.swm.reservation.controller;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.util.StringUtils;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class RefundController {
//
//    public InicisTxCancelResult cancel(CancelRequest cancelRequest, String tid, String payMethod) {
//        String inicisRefundUrl = UriComponentsBuilder.fromUriString(inicisUrl)
//                .path(REFUND_PATH)
//                .build()
//                .toUriString();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.add(HttpHeaders.ACCEPT, "*/*");
//
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//
//        // 전체취소는 Refund 값으로 고정
//        parameters.add("type", REFUND);
//        parameters.add("paymethod", payMethod);
//
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//        String localDateTime = LocalDateTime.now().format(dateTimeFormatter);
//        parameters.add("timestamp", localDateTime);
//
//        String clientIp = cancelRequest.getRemoteAddress();
//        parameters.add("clientIp", clientIp);
//
//        String mid = cancelRequest.isUsd() ? usdMid : krwMid;
//        parameters.add("mid", mid);
//        parameters.add("tid", tid);
//        parameters.add("msg", StringUtils.hasText(cancelRequest.getCancelMessage()) ? cancelRequest.getCancelMessage() : "");
//
//        // 파라미터를 암호화할 평문데이터가 필요
//        String plainText = iniPayApiKey + REFUND + payMethod + localDateTime + clientIp + mid + tid;
//        String hashData = createHashData(plainText);
//
//        if (StringUtils.hasText(hashData)) {
//            parameters.add("hashData", hashData);
//        }
//
//        HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(parameters, headers);
//
//        try {
//            InicisTxCancelResult inicisTxCancelResponse = restTemplate.exchange(inicisRefundUrl,
//                    HttpMethod.POST,
//                    formEntity,
//                    InicisTxCancelResult.class).getBody();
//
//            return inicisTxCancelResponse;
//        } catch (Exception e) {
//        }
//    }
//
//
//}