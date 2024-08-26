package kr.co.swm.adminPage.accommodation.controller;


import kr.co.swm.model.dto.WebDto;
import kr.co.swm.reservation.model.service.ReservationService;
import kr.co.swm.reservation.model.service.ReservationServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddressRestController {



    private final ReservationServiceImpl reservationService;

    public AddressRestController(ReservationServiceImpl reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/saveLocation1111")
    public String saveLocation(@RequestBody Map<String, Object> location) {

        System.out.println("진입");

        String latStr = location.get("lat").toString();
        String lonStr = location.get("lon").toString();

        String region = location.get("region").toString();
        String roadName = location.get("roadName").toString();

        double lat = Double.parseDouble(latStr);
        double lon = Double.parseDouble(lonStr);

        System.out.println("1 : " + lat);
        System.out.println("1 : " + lon);

        System.out.println("지번 : " + region);
        System.out.println("도로명 : " + roadName);
        return "success";

    }


}
