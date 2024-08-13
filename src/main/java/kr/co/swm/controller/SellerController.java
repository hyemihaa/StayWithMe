package kr.co.swm.controller;


import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.service.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class SellerController {

    private final SellerServiceImpl seller;

    @Autowired
    public SellerController(SellerServiceImpl seller) {
        this.seller = seller;
    }

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 요금 페이지 로드(객실 이름 리스트 조회)
    @GetMapping("/basic_rate_list.do")
    public String basicList(Model model) {
        int accommodationNo = 1;

        // 객실 이름 조회 호출(Service)
        List<String> roomName = seller.roomNameSearch(accommodationNo);

        // 조회 데이터 전달
        model.addAttribute("roomNameList", roomName);

        return "seller/basic_rate";
    }

    // 선택 된 ROOM NAME 요금 조회
    @GetMapping("/getRoomRates")
    @ResponseBody
    public Map<String, Object> rateSearch(@RequestParam("roomName") String roomName) {
        int accommodationNo = 1;

        // 기본 요금 조회
        List<SellerDto> basicRateList = seller.basicRateList(roomName, accommodationNo);

        // 추가 요금 조회
        List<SellerDto> extraRateList = seller.extraRateList(roomName, accommodationNo);

        for(SellerDto extraRate : extraRateList) {

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

        return response;
    }


    @PostMapping("/basic_rate_write.do")
    public String setRate(@ModelAttribute SellerDto sellerDto, RedirectAttributes redirectAttributes) {
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

        int accommodationNo = 1; // 임시로 설정된 숙박업소 번호

        // 기본 요금 업데이트 메서드 호출
        int basicRateUpdated = seller.basicRateUpdate(sellerDto, accommodationNo);

        // 추가 요금 업데이트 메서드 호출
        int extraRateUpdated = seller.extraRateUpdate(sellerDto, accommodationNo);

        // 업데이트 결과에 따라 처리
        if (basicRateUpdated > 0 || extraRateUpdated > 0) {
            redirectAttributes.addFlashAttribute("icon", "success");
            redirectAttributes.addFlashAttribute("title", "수정 성공");
            redirectAttributes.addFlashAttribute("text", "요금 수정이 정상적으로 완료되었습니다.");
            return "redirect:/basic_rate_list.do"; // 수정 성공 시 목록 페이지로 리다이렉트
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

    @GetMapping("/season_period.do")
    public String seasonList(Model model) {

        int accommodationNo = 1;  // 실제 accommodationNo에 맞게 설정

        // 추가 요금 조회
        List<SellerDto.ExtraDto> extraSeasonList = seller.extraSeasonList(accommodationNo);

        // extraRateList를 모델에 추가
        model.addAttribute("extraSeasonList", extraSeasonList);

        return "seller/season_period";
    }

    // 추가 요금 데이터 불러오는 메소드
    @GetMapping("/getExtraRates")
    @ResponseBody
    public List<SellerDto.ExtraDto> getExtraRates() {
        int accommodationNo = 1;  // 실제 accommodationNo에 맞게 설정

        return seller.getExtraRateInfo(accommodationNo);
    }

    // 기간 삭제 메소드
    @DeleteMapping("/deletePeriod")
    @ResponseBody
    public Map<String, Object> deletePeriod(@RequestParam String extraName) {
        int accommodationNo = 1;  // 실제 accommodationNo에 맞게 설정

        boolean success = seller.deleteExtraRateByName(extraName, accommodationNo);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);

        return response;
    }

    // 기간 수정 메소드
    @PostMapping("/updatePeriods")
    @ResponseBody
    public Map<String, Object> updatePeriods(@RequestBody List<SellerDto.ExtraDto> periods) {
        int accommodationNo = 1;  // 실제 accommodationNo에 맞게 설정

        boolean success = seller.updateExtraRates(periods, accommodationNo);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);

        return response;
    }

}


