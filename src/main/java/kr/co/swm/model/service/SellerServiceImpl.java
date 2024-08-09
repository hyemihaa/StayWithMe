package kr.co.swm.model.service;


import kr.co.swm.mappers.SellerMapper;
import kr.co.swm.model.dto.SellerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SellerServiceImpl implements SellerService{

    private final SellerMapper mapper;

    @Autowired
    public SellerServiceImpl(SellerMapper mapper) {

        this.mapper = mapper;
    }

    // 객실 리스트 조회
    @Override
    public List<String> roomNameSearch(int accommodationNo) {
        return mapper.roomNameSearch(accommodationNo);
    }

    // 기본 요금 조회
    @Override
    public List<SellerDto> basicRateList(String roomName) {
        List<SellerDto> basicRateList = mapper.basicRateList(roomName);
        return processRates(basicRateList, false);
    }

    // 추가 요금 조회
    @Override
    public List<SellerDto> extraRateList(String roomName) {
        List<SellerDto> extraRateList = mapper.extraRateList(roomName);
        return processRates(extraRateList, true);
    }






    private List<SellerDto> processRates(List<SellerDto> rateList, boolean isExtraRate) {
        Map<String, SellerDto> rateMap = new HashMap<>();

        for (SellerDto dto : rateList) {
            String roomName = dto.getRoomName();
            String extraName = dto.getExtraName();

            String key = roomName + ":" + extraName;
            SellerDto existingDto = rateMap.get(key);

            if (existingDto == null) {
                // 없으면 새로 생성하고 추가
                existingDto = new SellerDto();
                existingDto.setExtraName(dto.getExtraName());
                existingDto.setRoomName(roomName);
                rateMap.put(key, existingDto);
            }

            // 요일별로 데이터를 기존 객체에 채워 넣음
            if (isExtraRate) {
                switch (dto.getExtraDayNo()) {
                    case 1:
                        existingDto.setExtraWeekdayRate(dto.getExtraRate());
                        break;
                    case 2:
                        existingDto.setExtraFridayRate(dto.getExtraRate());
                        break;
                    case 3:
                        existingDto.setExtraSaturdayRate(dto.getExtraRate());
                        break;
                    case 4:
                        existingDto.setExtraSundayRate(dto.getExtraRate());
                        break;
                }
            } else {
                switch (dto.getBasicDayNo()) {
                    case 1:
                        existingDto.setWeekdayRate(dto.getRoomRate());
                        break;
                    case 2:
                        existingDto.setFridayRate(dto.getRoomRate());
                        break;
                    case 3:
                        existingDto.setSaturdayRate(dto.getRoomRate());
                        break;
                    case 4:
                        existingDto.setSundayRate(dto.getRoomRate());
                        break;
                }
            }
        }

        // 결과를 리스트로 반환
        return new ArrayList<>(rateMap.values());
    }


}


