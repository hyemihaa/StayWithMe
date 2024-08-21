package kr.co.swm.controller;

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

        // 대시보드 데이터를 디버깅하기 위해 출력합니다.
        for (WebDto item : dashboardData) {
            System.out.println("=============== Controller DashBoard Data ===============");
            System.out.println("Accommodation Type : " + item.getAccommodationType());
            System.out.println("Reservation Date : " + item.getReservationDate());
            System.out.println("Reservation Count : " + item.getReservationCount());
            System.out.println("Cancellation Count : " + item.getCancellationCount());
            System.out.println("Reservation Amount : " + item.getReservationAmount());
            System.out.println("Views Date : " + item.getViewsDate());
            System.out.println("Views Count : " + item.getViewsCount());
            System.out.println("========================================================");
        }

        // 대시보드 데이터의 첫 번째 요소는 요약 데이터를 포함하고 있습니다.
        WebDto summary = dashboardData.get(0);

        // 요약 데이터가 올바르게 설정되었는지 디버깅하기 위해 출력합니다.
        System.out.println("=============== Controller Summary Data ===============");
        System.out.println(summary.getViewsCount());
        System.out.println(summary.getReservationCount());
        System.out.println(summary.getCancellationCount());
        System.out.println(summary.getReservationAmount());
        System.out.println("========================================================");

        // 요약 데이터를 모델에 추가하여 뷰로 전달합니다.
        int viewsCount = summary.getViewsCount();
        int reservationCount = summary.getReservationCount();
        int cancellationCount = summary.getCancellationCount();
        int confirmedAmount = summary.getReservationAmount();

        model.addAttribute("viewsCount", viewsCount);
        model.addAttribute("reservationCount", reservationCount);
        model.addAttribute("cancellationCount", cancellationCount);
        model.addAttribute("confirmedAmount", confirmedAmount);

        // 숙박 형태별 매출 현황 데이터를 필터링하여 모델에 추가합니다.
        List<WebDto> accommodationData = dashboardData.stream()
                .filter(dto -> dto.getAccommodationType() != null) // 숙박 유형이 존재하는 데이터만 필터링
                .collect(Collectors.toList());
        model.addAttribute("accommodationData", accommodationData);

        // 월별 매출 현황 데이터를 필터링하여 모델에 추가합니다.
        List<WebDto> monthlySalesData = dashboardData.stream()
                .filter(dto -> dto.getReservationDate() != null && dto.getAccommodationType() == null) // 예약 날짜가 있고 숙박 유형이 없는 데이터만 필터링
                .collect(Collectors.toList());
        model.addAttribute("monthlySalesData", monthlySalesData);

        // 최근 매출 현황 데이터를 필터링하여 모델에 추가합니다.
        List<WebDto> recentSalesData = dashboardData.stream()
                .filter(dto -> dto.getReservationDate() != null && dto.getAccommodationType() != null) // 예약 날짜와 숙박 유형이 모두 존재하는 데이터만 필터링
                .collect(Collectors.toList());
        model.addAttribute("recentSalesData", recentSalesData);

        // 뷰 페이지를 반환합니다.
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
