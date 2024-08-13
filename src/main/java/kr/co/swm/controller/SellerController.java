package kr.co.swm.controller;


import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.service.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SellerController {

    private final SellerServiceImpl seller;

    @Autowired
    public SellerController(SellerServiceImpl seller) {
        this.seller = seller;
    }

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
        int accommodationNo = 1;

        // 기본 요금 업데이트
        int basicRateUpdated = seller.basicRateUpdate(sellerDto, accommodationNo);

        // 추가 요금 업데이트
        int extraRateUpdated = seller.extraRateUpdate(sellerDto, accommodationNo);

        // 결과 처리
        if (basicRateUpdated > 0 || extraRateUpdated > 0) {
            redirectAttributes.addFlashAttribute("icon", "success");
            redirectAttributes.addFlashAttribute("title", "수정 성공");
            redirectAttributes.addFlashAttribute("text", "요금 수정이 정상적으로 완료되었습니다.");
            return "redirect:/basic_rate_list.do";
        } else {
            redirectAttributes.addFlashAttribute("icon", "error");
            redirectAttributes.addFlashAttribute("title", "수정 실패");
            redirectAttributes.addFlashAttribute("text", "요금 수정에 실패하였습니다.");
            return "common/error";
        }
    }
}


