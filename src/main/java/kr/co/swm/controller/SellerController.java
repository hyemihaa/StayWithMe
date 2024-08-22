package kr.co.swm.controller;


import kr.co.swm.jwt.util.JWTUtil;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.service.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SellerController {

    private final SellerServiceImpl seller;
    private final JWTUtil jwtUtil;

    @Autowired
    public SellerController(SellerServiceImpl seller, JWTUtil jwtUtil) {
        this.seller = seller;
        this.jwtUtil = jwtUtil;
    }


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□


    @GetMapping("/seller-main.do")
    public String mainDashBoard(Model model, @CookieValue(value = "Authorization", required = false) String token) {

        // 관리자 번호(업소키)
        Long accommodationNo= jwtUtil.getAccommAdminKeyFromToken(token);

        // 업소 조회수 가져오기
        int roomViews = seller.roomViews(accommodationNo);
        model.addAttribute("roomViews", roomViews);

        // 대시보드 정보 (예약 및 결제 정보) 가져오기
        List<SellerDto> mainList = seller.mainList(accommodationNo);
        model.addAttribute("mainList", mainList);

        // 오늘 날짜 설정
        LocalDate today = LocalDate.now();
        String todayDate = today.toString(); // yyyy-MM-dd 형식으로 오늘 날짜를 얻음
        model.addAttribute("today", today);

        // 오늘 날짜에 해당하는 예약 리스트 필터링
        List<SellerDto> todayList = mainList.stream()
                .filter(item -> todayDate.equals(item.getReservationDate()))
                .collect(Collectors.toList());

        // 오늘 날짜에 해당하는 예약 건수 계산
        int todayReservationCount = todayList.size();
        model.addAttribute("reservationCount", todayReservationCount);

        // 오늘 날짜에 대한 결제 건수 계산
        long todayPaymentCount = todayList.stream()
                .filter(item -> "Confirmed".equals(item.getReservationStatus()) || "Completed".equals(item.getReservationStatus()))
                .count();
        model.addAttribute("paymentCount", todayPaymentCount);

        // 오늘 날짜에 대한 취소 건수 계산
        long todayCancelCount = todayList.stream()
                .filter(item -> "Cancelled".equals(item.getReservationStatus()))
                .count();
        model.addAttribute("cancelCount", todayCancelCount);

        // 오늘 날짜에 대한 매출액 계산
        int todayTotalAmount = todayList.stream()
                .filter(item -> "Confirmed".equals(item.getReservationStatus()) || "Completed".equals(item.getReservationStatus()))
                .mapToInt(SellerDto::getReserveAmount)
                .sum();
        int todayCancelledAmount = todayList.stream()
                .filter(item -> "Cancelled".equals(item.getReservationStatus()))
                .mapToInt(SellerDto::getReserveAmount)
                .sum();
        int netAmount = todayTotalAmount - todayCancelledAmount;
        model.addAttribute("netAmount", netAmount);

        // 예약 타입 결정 (오늘 예약이 없으면 N/A)
        String reservationType = todayList.isEmpty() ? "N/A" : todayList.get(0).getReservationType();
        model.addAttribute("reservationType", reservationType);

        // 현재 월 계산
        int currentMonth = today.getMonthValue();

        // 1월부터 현재 월까지의 레이블 생성
        List<String> monthlyLabels = new ArrayList<>();
        for (int i = 1; i <= currentMonth; i++) {
            monthlyLabels.add(String.format("%02d월", i));  // 월을 2자리 형식으로 출력
        }

        // 월별 예약, 취소, 결제 건수 계산을 위한 Map 초기화
        Map<String, Integer> monthlyReservationCounts = new LinkedHashMap<>();
        Map<String, Integer> monthlyCancelCounts = new LinkedHashMap<>();
        Map<String, Integer> monthlyPaymentCounts = new LinkedHashMap<>();

        // 월별 예약, 취소, 결제 건수를 0으로 초기화
        for (String month : monthlyLabels) {
            monthlyReservationCounts.put(month, 0);
            monthlyCancelCounts.put(month, 0);
            monthlyPaymentCounts.put(month, 0);
        }

        // mainList를 통해 월별 예약, 취소, 결제 건수를 계산
        for (SellerDto item : mainList) {
            String reservationMonth = item.getReservationDate().substring(5, 7) + "월"; // 월만 추출
            if (monthlyReservationCounts.containsKey(reservationMonth)) {
                monthlyReservationCounts.put(reservationMonth, monthlyReservationCounts.get(reservationMonth) + 1);
            }
            if ("Cancelled".equals(item.getReservationStatus()) && monthlyCancelCounts.containsKey(reservationMonth)) {
                monthlyCancelCounts.put(reservationMonth, monthlyCancelCounts.get(reservationMonth) + 1);
            }
            if (("Confirmed".equals(item.getReservationStatus()) || "Completed".equals(item.getReservationStatus())) &&
                    monthlyPaymentCounts.containsKey(reservationMonth)) {
                monthlyPaymentCounts.put(reservationMonth, monthlyPaymentCounts.get(reservationMonth) + 1);
            }
        }

        // 모델에 월별 데이터 추가
        model.addAttribute("monthlyLabels", monthlyLabels);
        model.addAttribute("monthlyReservationCounts", new ArrayList<>(monthlyReservationCounts.values()));
        model.addAttribute("monthlyCancelCounts", new ArrayList<>(monthlyCancelCounts.values()));
        model.addAttribute("monthlyPaymentCounts", new ArrayList<>(monthlyPaymentCounts.values()));

        // 디버깅 로그 출력
        System.out.println("monthlyLabels: " + monthlyLabels);
        System.out.println("monthlyReservationCounts: " + monthlyReservationCounts.values());
        System.out.println("monthlyCancelCounts: " + monthlyCancelCounts.values());
        System.out.println("monthlyPaymentCounts: " + monthlyPaymentCounts.values());

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);

        return "seller/seller"; // 뷰 이름 반환
    }


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 예약 리스트 페이지 로드
    @GetMapping("/reservation.do")
    public String reservation(Model model, @CookieValue(value = "Authorization", required = false) String token) {

        // 관리자 번호(업소키)
        Long accommodationNo = jwtUtil.getAccommAdminKeyFromToken(token);

        // 예약 및 결제 정보 가져오기
        List<SellerDto> reservation = seller.mainList(accommodationNo);

        for (SellerDto item : reservation) {
            System.out.println();
            System.out.println("= = = = = = reservation controller = = = = = =");
            System.out.println("예약 정보 : " + item.getReserveRoomName());
            System.out.println("입실일 : " + item.getReserveCheckIn());
            System.out.println("퇴실일 : " + item.getReserveCheckOut());
            System.out.println("예약자 : " + item.getUserName());
            System.out.println("예약연락처 : " + item.getUserPhone());
            System.out.println("결제금액 : " + item.getReserveAmount());
            System.out.println("예약상태 : " + item.getReservationStatus());
            System.out.println("예약취소일 : " + item.getReservationCancellationDate());
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println();
        }

        model.addAttribute("reservation", reservation);

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);

        return "seller/reservation";
    }

    // 특정 검색 조건으로 예약 리스트 조회
    @PostMapping("/reservation-search.do")
    public String reservationSearch(@RequestParam(value = "dateType", required = false) String dateType,
                                    @RequestParam(value = "startDate", required = false) String startDate,
                                    @RequestParam(value = "endDate", required = false) String endDate,
                                    @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                                    @RequestParam(value = "reservationStatus", required = false) String reservationStatus,
                                    @CookieValue(value = "Authorization", required = false) String token,
                                    Model model) {

        System.out.println("<<<<<<<<<<<< 입력값 확인 디버깅 >>>>>>>>>>>>>>");
        System.out.println("dateType: " + dateType);
        System.out.println("startDate: " + startDate);
        System.out.println("endDate: " + endDate);
        System.out.println("searchKeyword: " + searchKeyword);
        System.out.println("reservationStatus: " + reservationStatus);
        System.out.println("============================================");

        // 관리자 번호(업소키)
        Long accommodationNo= jwtUtil.getAccommAdminKeyFromToken(token);

        // 예약 및 결제 정보 가져오기
        List<SellerDto> reservationSearch = seller.reservationSearch(accommodationNo, dateType, startDate, endDate, searchKeyword, reservationStatus);

        for (SellerDto item : reservationSearch) {
            System.out.println();
            System.out.println("= = = = reservationSearch controller = = = = =");
            System.out.println("예약 정보 : " + item.getReserveRoomName());
            System.out.println("입실일 : " + item.getReserveCheckIn());
            System.out.println("퇴실일 : " + item.getReserveCheckOut());
            System.out.println("예약자 : " + item.getUserName());
            System.out.println("예약연락처 : " + item.getUserPhone());
            System.out.println("결제금액 : " + item.getReserveAmount());
            System.out.println("예약상태 : " + item.getReservationStatus());
            System.out.println("예약취소일 : " + item.getReservationCancellationDate());
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println();
        }

        model.addAttribute("reservationSearch", reservationSearch);

        // 검색 조건들을 모델에 담아 다시 전달
        model.addAttribute("dateType", dateType);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("reservationStatus", reservationStatus);

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);

        return "seller/reservation";
    }


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 일별 현황 조회
    @GetMapping("/reservation-daily.do")
    public String reservationDaily(@RequestParam(value = "date", required = false) String date, Model model, @CookieValue(value = "Authorization", required = false) String token) {

        // 관리자 번호(업소키)
        Long accommodationNo= jwtUtil.getAccommAdminKeyFromToken(token);

        // 선택된 날짜가 없으면 오늘 날짜로 설정
        LocalDate selectedDate = (date == null) ? LocalDate.now() : LocalDate.parse(date);
        String selectedDateString = selectedDate.toString(); // yyyy-MM-dd 형식으로 날짜를 문자열로 변환
        model.addAttribute("selectedDate", selectedDateString); // 선택된 날짜를 모델에 추가하여 뷰에 전달

        // DB에서 선택된 날짜에 해당하는 객실 정보를 조회
        List<SellerDto> roomData = seller.roomData(accommodationNo, selectedDateString);

        // 조회된 객실 정보 디버깅 출력
        for (SellerDto item : roomData) {
            System.out.println();
            System.out.println("========= Controller Room Data =========");
            System.out.println("ROOM NO : " + item.getRoomNo());
            System.out.println("ROOM TYPE : " + item.getRoomTypeName());
            System.out.println("ROOM NAME : " + item.getRoomName());
            System.out.println("ROOM CHECK IN : " + item.getRoomCheckIn());
            System.out.println("ROOM CHECK OUT : " + item.getRoomCheckOut());
            System.out.println("ROOM Status : " + item.getReservationStatus());
            System.out.println("========================================");
            System.out.println();
        }

        // 각 방의 예약 상태를 계산 및 업데이트
        for (SellerDto room : roomData) {
            String reservationStatus = room.getReservationStatus();

            // 예약 상태에 따라 텍스트를 업데이트
            if (reservationStatus == null || reservationStatus.equals("Cancelled")) {
                room.setReservationStatus("예약 가능");
            } else if (reservationStatus.equals("Confirmed")) {
                room.setReservationStatus("예약중");
            }
        }

        // 업데이트된 객실 정보를 모델에 추가하여 뷰에 전달
        model.addAttribute("roomData", roomData);

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);

        // 예약 상태가 반영된 뷰 페이지를 반환
        return "seller/reservation_daily";
    }



//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 월별 현황 조회
    @GetMapping("/reservation-monthly.do")
    public String reservationMonthly(Model model, @CookieValue(value = "Authorization", required = false) String token) {

        // 관리자 번호(업소키)
        Long accommodationNo= jwtUtil.getAccommAdminKeyFromToken(token);

        // 관리자 보유 객실 조회
        List<SellerDto> accommodationRoomData = seller.accommodationRoomData(accommodationNo);

        // 해당 데이터를 페이지 로드시에 Thymeleaf 템플릿으로 전달
        model.addAttribute("accommodationRoomData", accommodationRoomData);

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);

        return "seller/reservation_monthly";  // 뷰 이름 반환
    }

    // 비동기 요청 처리
    @GetMapping("/reservation-monthly-data")
    @ResponseBody
    public Map<String, Object> getReservationMonthlyData(Model model, @CookieValue(value = "Authorization", required = false) String token) {

        // 관리자 번호(업소키)
        Long accommodationNo= jwtUtil.getAccommAdminKeyFromToken(token);
        
        // 관리자 보유 객실 조회
        List<SellerDto> accommodationRoomData = seller.accommodationRoomData(accommodationNo);

        // 관리자 객실 예약 조회
        List<SellerDto> monthlyData = seller.monthlyData(accommodationNo);

        // 데이터를 맵에 담아 반환
        Map<String, Object> result = new HashMap<>();
        result.put("accommodationRoomData", accommodationRoomData);
        result.put("monthlyData", monthlyData);

        // 로그로 데이터 확인
        System.out.println("Accommodation Room Data: " + accommodationRoomData);
        System.out.println("Monthly Data: " + monthlyData);

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);

        return result;  // JSON으로 반환
    }




//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□



    // 요금 페이지 로드(객실 이름 리스트 조회)
    @GetMapping("/basic-rate-list.do")
    public String basicList(Model model, @CookieValue(value = "Authorization", required = false) String token) {

        // 관리자 번호(업소키)
        Long accommodationNo= jwtUtil.getAccommAdminKeyFromToken(token);

        // 객실 이름 조회 호출(Service)
        List<String> roomName = seller.roomNameSearch(accommodationNo);

        // 조회 데이터 전달
        model.addAttribute("roomNameList", roomName);

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);

        return "seller/basic_rate";
    }

    // 선택 된 ROOM NAME 요금 조회
    @GetMapping("/getRoomRates")
    @ResponseBody
    public Map<String, Object> rateSearch(Model model, @RequestParam("roomName") String roomName, @CookieValue(value = "Authorization", required = false) String token) {

        // 관리자 번호(업소키)
        Long accommodationNo= jwtUtil.getAccommAdminKeyFromToken(token);

        // 기본 요금 조회
        List<SellerDto> basicRateList = seller.basicRateList(roomName, accommodationNo);

        // 추가 요금 조회
        List<SellerDto> extraRateList = seller.extraRateList(roomName, accommodationNo);

        for (SellerDto extraRate : extraRateList) {

            List<SellerDto.ExtraDto> extraRates = extraRate.getExtraRates();
            for (SellerDto.ExtraDto extraDto : extraRates) {
                System.out.println("====================================================");
                System.out.println("Extra Name: " + extraDto.getExtraName());
                System.out.println("Extra Date Start: " + extraDto.getExtraDateStart());
                System.out.println("Extra Date End: " + extraDto.getExtraDateEnd());
                System.out.println("Extra Weekday Rate: " + extraDto.getExtraWeekdayRate());
                System.out.println("Extra Friday Rate: " + extraDto.getExtraFridayRate());
                System.out.println("Extra Saturday Rate: " + extraDto.getExtraSaturdayRate());
                System.out.println("Extra Sunday Rate: " + extraDto.getExtraSundayRate());
                System.out.println("====================================================");
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("basicRates", basicRateList.isEmpty() ? null : basicRateList.get(0));
        response.put("extraRates", extraRateList.isEmpty() ? null : extraRateList);

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);

        return response;
    }

    // 기본 요금 및 추가 요금 수정/삽입
    @PostMapping("/basic-rate-write.do")
    public String setRate(@ModelAttribute SellerDto sellerDto, RedirectAttributes redirectAttributes, @CookieValue(value = "Authorization", required = false) String token) {
        // 필터링된 유효한 추가 요금 정보 출력 (디버깅용)
        System.out.println("========== 입력된 기본 요금 정보 ==========");
        System.out.println("Room Name: " + sellerDto.getRoomName());
        System.out.println("Weekday Rate: " + sellerDto.getWeekdayRate());
        System.out.println("Friday Rate: " + sellerDto.getFridayRate());
        System.out.println("Saturday Rate: " + sellerDto.getSaturdayRate());
        System.out.println("Sunday Rate: " + sellerDto.getSundayRate());

        // 추가 요금 리스트 중 유효한 데이터만 필터링하고 날짜 값을 설정
        List<SellerDto.ExtraDto> validExtraRates = sellerDto.getExtraRates().stream()
                .filter(extraRate -> isValidExtraRate(extraRate)) // 유효성 검사 통과 여부 확인
                .map(this::setDefaultDatesIfNecessary) // 날짜 설정
                .collect(Collectors.toList());

        System.out.println("========== 입력된 추가 요금 정보 ==========");
        for (SellerDto.ExtraDto extraRate : validExtraRates) {
            // 필터링된 유효한 추가 요금 정보 출력 (디버깅용)
            System.out.println("Extra Name: " + extraRate.getExtraName());
            System.out.println("Extra Weekday Rate: " + extraRate.getExtraWeekdayRate());
            System.out.println("Extra Friday Rate: " + extraRate.getExtraFridayRate());
            System.out.println("Extra Saturday Rate: " + extraRate.getExtraSaturdayRate());
            System.out.println("Extra Sunday Rate: " + extraRate.getExtraSundayRate());
            System.out.println("Extra Date Start: " + extraRate.getExtraDateStart());
            System.out.println("Extra Date End: " + extraRate.getExtraDateEnd());
            System.out.println("==================================================");
        }

        // 필터링된 유효한 추가 요금 리스트로 업데이트
        sellerDto.setExtraRates(validExtraRates);

        // 관리자 번호(업소키)
        Long accommodationNo= jwtUtil.getAccommAdminKeyFromToken(token);

        // 기본 요금 업데이트 메서드 호출
        int basicRateUpdated = seller.basicRateUpdate(sellerDto, accommodationNo);

        // 추가 요금 업데이트 메서드 호출
        int extraRateUpdated = seller.extraRateUpdate(sellerDto, accommodationNo);

        // 업데이트 결과에 따라 처리
        if (basicRateUpdated > 0 || extraRateUpdated > 0) {
            redirectAttributes.addFlashAttribute("icon", "success");
            redirectAttributes.addFlashAttribute("title", "수정 성공");
            redirectAttributes.addFlashAttribute("text", "요금 수정이 정상적으로 완료되었습니다.");
            return "redirect:/basic-rate-list.do"; // 수정 성공 시 목록 페이지로 리다이렉트
        } else {
            redirectAttributes.addFlashAttribute("icon", "error");
            redirectAttributes.addFlashAttribute("title", "수정 실패");
            redirectAttributes.addFlashAttribute("text", "요금 수정에 실패하였습니다.");
            return "common/error"; // 수정 실패 시 오류 페이지로 리다이렉트
        }
    }

    // 추가 요금의 유효성을 검사하는 메서드
    private boolean isValidExtraRate(SellerDto.ExtraDto extraRate) {
        return extraRate.getExtraName() != null && !extraRate.getExtraName().trim().isEmpty() // 이름이 비어있지 않은지 확인
                && extraRate.getExtraWeekdayRate() > 0 // 평일 요금이 0보다 큰지 확인
                && extraRate.getExtraFridayRate() > 0 // 금요일 요금이 0보다 큰지 확인
                && extraRate.getExtraSaturdayRate() > 0 // 토요일 요금이 0보다 큰지 확인
                && extraRate.getExtraSundayRate() > 0; // 일요일 요금이 0보다 큰지 확인
    }

    // EXTRA_DATE_START와 EXTRA_DATE_END 값을 기본값으로 설정하는 메서드
    private SellerDto.ExtraDto setDefaultDatesIfNecessary(SellerDto.ExtraDto extraRate) {
        if (extraRate.getExtraDateStart() == null || extraRate.getExtraDateStart().trim().isEmpty()) {
            // 시작일이 없으면 현재 날짜를 시작일로 설정
            String today = LocalDate.now().toString();
            extraRate.setExtraDateStart(today);

            // 종료일을 시작일로부터 30일 후로 설정
            String endDate = LocalDate.now().plusDays(30).toString();
            extraRate.setExtraDateEnd(endDate);
        }
        return extraRate;
    }

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 기간 수정 페이지 로드(추가 요금 정보 조회)
    @GetMapping("/season-period.do")
    public String seasonList(Model model, @CookieValue(value = "Authorization", required = false) String token) {

        // 관리자 번호(업소키)
        Long accommodationNo= jwtUtil.getAccommAdminKeyFromToken(token);

        // 추가 요금 조회
        List<SellerDto.ExtraDto> extraSeasonList = seller.extraSeasonList(accommodationNo);

        // extraRateList를 모델에 추가
        model.addAttribute("extraSeasonList", extraSeasonList);

        // 사용자 이름
        String userId = jwtUtil.getUserIdFromToken(token);
        model.addAttribute("userId", userId);

        return "seller/season_period";
    }

    // 요금타입 삭제 요청을 처리하는 메소드
    @DeleteMapping("/extra-delete")
    @ResponseBody
    public Map<String, Object> extraRateDelete(@RequestParam("extraName") String extraName, @CookieValue(value = "Authorization", required = false) String token) {

        // 관리자 번호(업소키)
        Long accommodationNo= jwtUtil.getAccommAdminKeyFromToken(token);
        Map<String, Object> response = new HashMap<>();

        // 해당 요금타입 삭제
        boolean isDeleted = seller.extraRateDelete(extraName, accommodationNo);

        // 삭제 결과를 클라이언트에 반환
        response.put("success", isDeleted);

        return response; // 삭제 성공 여부 반환
    }

    // 기간 수정 요청을 처리하는 메소드
    @PostMapping("/periods-update")
    @ResponseBody
    public Map<String, Object> updatePeriods(@ModelAttribute SellerDto sellerDto, @CookieValue(value = "Authorization", required = false) String token) {

        // 관리자 번호(업소키)
        Long accommodationNo= jwtUtil.getAccommAdminKeyFromToken(token);

        // DTO의 내용을 출력하여 확인
        System.out.println("Received Periods: " + sellerDto.getExtraRates());

        for (SellerDto.ExtraDto period : sellerDto.getExtraRates()) {
            System.out.println("Received Period: " + period.getExtraName() + ", Start: " + period.getExtraDateStart() + ", End: " + period.getExtraDateEnd());
        }

        boolean updateSuccess = seller.extraSeasonUpdate(sellerDto.getExtraRates(), accommodationNo);

        Map<String, Object> response = new HashMap<>();
        response.put("success", updateSuccess);

        return response;
    }


}

