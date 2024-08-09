package kr.co.swm.controller;


import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.service.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

        // 관리자키 지정(이후 Token으로 변경 예정)
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

        // 기본 요금 조회
        List<SellerDto> basicRateList = seller.basicRateList(roomName);

        // 추가 요금 조회
        List<SellerDto> extraRateList = seller.extraRateList(roomName);

        for(SellerDto item : extraRateList) {
            System.out.println(" 배열 확인  :  " + item.getExtraName());
            System.out.println(" 객실 이름  :  " + item.getRoomName());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("basicRates", basicRateList.isEmpty() ? null : basicRateList.get(0));
        response.put("extraRates", extraRateList);

        return response;
    }



}

