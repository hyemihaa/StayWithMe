package kr.co.swm.adminPage.accommodation.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddressRestController {




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
