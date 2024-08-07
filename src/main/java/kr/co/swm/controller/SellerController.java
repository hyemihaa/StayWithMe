package kr.co.swm.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.service.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class SellerController {

    private final SellerServiceImpl seller;

    @Autowired
    public SellerController(SellerServiceImpl seller) {

        this.seller = seller;
    }

//    << 페이지 로드 및 기존 요금 정보 조회 >>
    @GetMapping("/basic_rate_List.do")
    public String basicList(Model model) {
        String roomCode = "PRV001";

        List<SellerDto> list = seller.basicRate(roomCode);

        // Set을 사용하여 중복된 roomName 제거
        Set<String> roomNames = new HashSet<>();
        for (SellerDto item : list) {
            roomNames.add(item.getRoomName());
        }

        // 중복 제거된 roomNames를 리스트로 변환
        List<String> uniqueRoomNames = new ArrayList<>(roomNames);

        // 요금 타입별로 데이터를 병합하여 새로운 리스트 생성
        Map<String, SellerDto> mergedMap = new HashMap<>();
        for (SellerDto item : list) {
            String key = item.getRoomType();
            if (!mergedMap.containsKey(key)) {
                SellerDto dto = new SellerDto();
                dto.setRoomType(key);
                mergedMap.put(key, dto);
            }

            // 요일별로 roomRate를 적절한 필드에 저장
            switch (item.getDayNo()) {
                case 1:
                    mergedMap.get(key).setWeekdayRate(item.getRoomRate());
                    break;
                case 2:
                    mergedMap.get(key).setFridayRate(item.getRoomRate());
                    break;
                case 3:
                    mergedMap.get(key).setSaturdayRate(item.getRoomRate());
                    break;
                case 4:
                    mergedMap.get(key).setSundayRate(item.getRoomRate());
                    break;
            }
        }

        List<SellerDto> processedList = new ArrayList<>(mergedMap.values());

        model.addAttribute("sellerList", processedList);
        model.addAttribute("uniqueRoomNames", uniqueRoomNames);
        return "saller/basic_rate";
    }

//    << 요금 수정 및 신규 등록 기능 >>
    @PostMapping("/basic_rate.do")
    public String basicWrite(@ModelAttribute SellerDto sellerDto, HttpSession session) {

        String adminCode = "PRV001";
        int result = seller.basicRateInsert(sellerDto, adminCode);
        return "redirect:/basic_rate_List.do";  // 처리 후 리다이렉트할 페이지 설정
    }



}
