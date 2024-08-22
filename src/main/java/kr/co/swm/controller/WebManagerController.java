package kr.co.swm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.swm.model.dto.WebDto;
import kr.co.swm.model.service.WebServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebManagerController {

    private final WebServiceImpl web;

    @Autowired
    public WebManagerController(WebServiceImpl web) {
        this.web = web;
    }

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-login")
    public String webCenterLogin() {
        return "web_manager/webcenter_login";
    }




//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-center")
    public String webCenterDashboard(Model model) {

        // 서비스에서 가공된 대시보드 데이터를 가져옵니다.
        List<WebDto> dashboardData = web.dashboardData();

        // 오늘 날짜와 현재 연도를 구합니다.
        String today = java.time.LocalDate.now().toString();
        String currentYear = java.time.LocalDate.now().getYear() + "-";

        // 대시보드 데이터가 비어있지 않은지 확인하고, 첫 번째 요소를 요약 데이터로 사용
        if (!dashboardData.isEmpty()) {
            WebDto summary = dashboardData.get(0);

            model.addAttribute("viewsCount", summary.getViewsCount());
            model.addAttribute("reservationCount", summary.getReservationCount());
            model.addAttribute("cancellationCount", summary.getCancellationCount());
            model.addAttribute("confirmedAmount", summary.getReservationAmount());
        }

        // 숙박 형태별 매출 현황 데이터를 오늘 날짜로 필터링하여 모델에 추가
        List<WebDto> accommodationData = dashboardData.stream()
                .filter(dto -> dto.getAccommodationType() != null && today.equals(dto.getReservationDate())) // 오늘 날짜 데이터 필터링
                .collect(Collectors.toList());
        model.addAttribute("accommodationData", accommodationData);

        // 숙박 형태 디버깅
        for(WebDto item : accommodationData) {
            System.out.println("========== Controller Accommodation Data ==========");
            System.out.println("Accommodation Type : " + item.getAccommodationType());
            System.out.println("===================================================");
        }

        // 월별 매출 현황 데이터를 현재 연도 기준으로 필터링하여 모델에 추가
        List<WebDto> hotelSalesData = dashboardData.stream()
                .filter(dto -> dto.getReservationDate() != null
                        && dto.getReservationDate().startsWith(currentYear)
                        && "호텔".equals(dto.getAccommodationType())) // 현재 연도 및 숙소 타입이 '호텔'인 데이터 필터링
                .collect(Collectors.toList());

        List<WebDto> resortSalesData = dashboardData.stream()
                .filter(dto -> dto.getReservationDate() != null
                        && dto.getReservationDate().startsWith(currentYear)
                        && "리조트".equals(dto.getAccommodationType())) // 현재 연도 및 숙소 타입이 '리조트'인 데이터 필터링
                .collect(Collectors.toList());

        model.addAttribute("hotelSalesData", hotelSalesData);
        model.addAttribute("resortSalesData", resortSalesData);

        // 월별 매출 현황 디버깅
        for(WebDto item : hotelSalesData) {
            System.out.println("========== Controller Hotel Sales Data ==========");
            System.out.println("Reservation Date : " + item.getReservationDate());
            System.out.println("Accommodation Type : " + item.getAccommodationType());
            System.out.println("Reservation Amount : " + item.getReservationAmount());
            System.out.println("===================================================");
        }

        for(WebDto item : resortSalesData) {
            System.out.println("========== Controller Resort Sales Data ==========");
            System.out.println("Reservation Date : " + item.getReservationDate());
            System.out.println("Accommodation Type : " + item.getAccommodationType());
            System.out.println("Reservation Amount : " + item.getReservationAmount());
            System.out.println("===================================================");
        }

        // 최근 매출 현황 데이터를 오늘 날짜 기준으로 필터링하여 모델에 추가
        List<WebDto> recentSalesData = dashboardData.stream()
                .filter(dto -> dto.getReservationDate() != null && dto.getAccommodationType() != null && today.equals(dto.getReservationDate())) // 오늘 날짜 데이터 필터링
                .collect(Collectors.toList());
        model.addAttribute("recentSalesData", recentSalesData);

        // 최근 매출 현황 디버깅
        for(WebDto item : recentSalesData) {
            System.out.println("========== Controller Recent Sales Data ==========");
            System.out.println("Accommodation Type : " + item.getAccommodationType());
            System.out.println("Reservation Amount : " + item.getReservationAmount());
            System.out.println("===================================================");
        }

        // JSON 문자열로 변환하여 모델에 추가
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // 숙박 형태별 매출 데이터 JSON 변환
            String accommodationDataJson = objectMapper.writeValueAsString(accommodationData);

            // 호텔 및 리조트 월별 매출 데이터 JSON 변환
            String hotelSalesDataJson = objectMapper.writeValueAsString(hotelSalesData);
            String resortSalesDataJson = objectMapper.writeValueAsString(resortSalesData);

            // 최근 매출 데이터 JSON 변환
            String recentSalesDataJson = objectMapper.writeValueAsString(recentSalesData);

            // 모델에 JSON 데이터 추가
            model.addAttribute("accommodationDataJson", accommodationDataJson);
            model.addAttribute("hotelSalesDataJson", hotelSalesDataJson);
            model.addAttribute("resortSalesDataJson", resortSalesDataJson);
            model.addAttribute("recentSalesDataJson", recentSalesDataJson);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // 예외 처리 코드 추가 가능
        }


        return "web_manager/webcenter_dashboard";
    }




//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-coupon")
    public String webCenterCoupon(Model model) {


        return "web_manager/webcenter_coupon";
    }



//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-member")
    public String webCenterMember(Model model) {


        return "web_manager/webcenter_member_search";
    }


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-seller")
    public String webCenterSeller(Model model) {


        return "web_manager/webcenter_seller";
    }


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-manager")
    public String webCenterManager(Model model) {


        return "web_manager/webcenter_manager";
    }
}
