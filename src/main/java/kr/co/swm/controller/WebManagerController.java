package kr.co.swm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.swm.jwt.util.JWTUtil;
import kr.co.swm.model.dto.WebDto;
import kr.co.swm.model.service.WebServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebManagerController {

    private final WebServiceImpl web;
    private final JWTUtil jwtUtil;

    @Autowired
    public WebManagerController(WebServiceImpl web, JWTUtil jwtUtil) {

        this.web = web;
        this.jwtUtil = jwtUtil;
    }

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-login")
    public String webCenterLogin() {
        return "web_manager/webcenter_login";
    }




//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-center")
    public String webCenterDashboard(Model model, @CookieValue(value = "Authorization", required = false) String token) {

        // 오늘 날짜와 현재 연도를 구합니다.
        String today = LocalDate.now().toString();
        String currentYear = LocalDate.now().getYear() + "-";

        // 1. 서비스에서 조회수, 예약수, 취소수, 결제금액 데이터를 가져옵니다.
        WebDto summaryData = web.dashboardData(today);

            System.out.println();
            System.out.println("Confirmed Amount : " + summaryData.getReservationAmount());
            System.out.println();

        model.addAttribute("viewsCount", summaryData.getViewsCount());
        model.addAttribute("reservationCount", summaryData.getReservationCount());
        model.addAttribute("cancellationCount", summaryData.getCancellationCount());
        model.addAttribute("confirmedAmount", summaryData.getReservationAmount());

        // 2. 숙박 형태별 매출 현황 데이터를 가져옵니다.
        List<WebDto> accommodationData = web.getAccommodationRevenueData(today);
        String accommodationDataJson = convertToJson(accommodationData);

        // 3. 월별 매출 현황 데이터를 가져옵니다.
        List<WebDto> monthlySalesData = web.getMonthlySalesData(currentYear);
        String monthlySalesDataJson = convertToJson(monthlySalesData);

        // 4. 최근 매출 현황 데이터를 가져옵니다.
        List<WebDto> recentSalesData = web.getRecentSalesData(today);
        String recentSalesDataJson = convertToJson(recentSalesData);

        model.addAttribute("accommodationDataJson", accommodationDataJson);
        model.addAttribute("monthlySalesDataJson", monthlySalesDataJson);
        model.addAttribute("recentSalesDataJson", recentSalesDataJson);

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);

        return "web_manager/webcenter_dashboard";
    }

    private String convertToJson(List<WebDto> data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }




//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-coupon")
    public String webCenterCoupon(WebDto webDto, Model model, @CookieValue(value = "Authorization", required = false) String token) {
        prepareCouponPage(model, token);
        return "web_manager/webcenter_coupon";
    }

    @PostMapping("/web-coupon-save")
    public String saveCoupon(WebDto webDto, Model model, @CookieValue(value = "Authorization", required = false) String token) {

        System.out.println("COUPON NAME : " + webDto.getCouponName());
        System.out.println("COUPON TYPE : " + webDto.getCouponType());
        System.out.println("COUPON DISCOUNT : " + webDto.getCouponDiscount());
        System.out.println("COUPON QUANTITY : " + webDto.getCouponQuantity());
        System.out.println("COUPON START DATE : " + webDto.getCouponStartDate());
        System.out.println("COUPON END DATE : " + webDto.getCouponEndDate());
        System.out.println("COUPON MINIMUM AMOUNT : " + webDto.getCouponMinimumAmount());


        // 쿠폰 등록
        web.couponInsert(webDto);
        prepareCouponPage(model, token);
        return "web_manager/webcenter_coupon";
    }

    private void prepareCouponPage(Model model, String token) {
        // 모든 쿠폰 리스트를 조회
        List<WebDto> couponList = web.couponList();
        model.addAttribute("couponList", couponList);

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);
    }

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-member")
    public String webCenterMember(Model model, @CookieValue(value = "Authorization", required = false) String token) {

        List<WebDto> userSearch = new ArrayList<>();

        // 판매자 리스트와 관리자 리스트를 통합해서 하나의 리스트로 처리
        List<WebDto> userList = web.userSearch();
        List<WebDto> sellerList = web.sellerSearch();

        // 모든 사용자 데이터를 하나의 리스트에 통합
        userSearch.addAll(userList);
        userSearch.addAll(sellerList);

        model.addAttribute("userSearch", userSearch);

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);

        return "web_manager/webcenter_member_search";
    }

    @PostMapping("/user-search")
    public String userSearch(
            @RequestParam("userType") String userType,  // 폼에서 전달된 사용자 타입 (일반 사용자 또는 판매자)
            @RequestParam("searchKeyword") String searchKeyword,  // 폼에서 전달된 검색어
            @CookieValue(value = "Authorization", required = false) String token,
            Model model) {

        List<WebDto> userSearch = new ArrayList<>();  // 결과를 저장할 리스트를 초기화

        // 사용자 타입이 '일반 사용자'일 경우
        if ("general".equals(userType)) {
            if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
                // 검색어가 없으면 모든 일반 사용자를 조회
                userSearch = web.userSearch();
            } else {
                // 검색어가 있으면 해당 검색어를 포함하는 일반 사용자 조회
                userSearch = web.searchUsersByKeyword(searchKeyword);
            }
        }
        // 사용자 타입이 '판매자'일 경우
        else if ("seller".equals(userType)) {
            if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
                // 검색어가 없으면 모든 판매자를 조회
                userSearch = web.sellerSearch();
            } else {
                // 검색어가 있으면 해당 검색어를 포함하는 판매자 조회
                userSearch = web.searchSellersByKeyword(searchKeyword);
            }
        }

        // 검색 결과 리스트를 모델에 추가하여 뷰로 전달
        model.addAttribute("userSearch", userSearch);
        // 검색어를 모델에 추가하여 뷰에서 검색어를 유지할 수 있도록 설정
        model.addAttribute("searchKeyword", searchKeyword);
        // 사용자 타입을 모델에 추가하여 뷰에서 선택된 사용자 타입을 유지할 수 있도록 설정
        model.addAttribute("userType", userType);

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);

        return "web_manager/webcenter_member_search";  // 검색 결과를 보여줄 뷰 이름 반환
    }



//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-seller")
    public String webCenterSeller(Model model, @CookieValue(value = "Authorization", required = false) String token) {

        // 판매자 리스트를 조회하는 서비스 호출
        List<WebDto> sellerList = web.sellerSearch();

        // 조회된 판매자 리스트를 모델에 추가
        model.addAttribute("sellerList", sellerList);

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);

        return "web_manager/webcenter_seller";
    }


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @GetMapping("/web-manager")
    public String webCenterManager(Model model, @CookieValue(value = "Authorization", required = false) String token) {

        // 판매자 리스트를 조회하는 서비스 호출
        List<WebDto> managerList = web.managerSearch();

        // 조회된 관리자 리스트를 모델에 추가
        model.addAttribute("managerList", managerList);

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);

        return "web_manager/webcenter_manager";
    }
}
