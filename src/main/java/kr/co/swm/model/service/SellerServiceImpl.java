package kr.co.swm.model.service;


import kr.co.swm.mappers.SellerMapper;
import kr.co.swm.model.dto.SellerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SellerServiceImpl implements SellerService {

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
    public List<SellerDto> basicRateList(String roomName, int accommodationNo) {
        List<SellerDto> basicRateList = mapper.basicRateList(roomName, accommodationNo);
        return processRates(basicRateList, false);
    }

    // 추가 요금 조회
    @Override
    public List<SellerDto> extraRateList(String roomName, int accommodationNo) {
        List<SellerDto> extraRateList = mapper.extraRateList(roomName, accommodationNo);

        for (SellerDto sellerDto : extraRateList) {
            System.out.println("Room Name: " + sellerDto.getRoomName());

            // 각 SellerDto 객체의 ExtraDto 리스트에 접근
            List<SellerDto.ExtraDto> extraRates = sellerDto.getExtraRates();
            for (SellerDto.ExtraDto extraDto : extraRates) {
                System.out.println("Room No: " + extraDto.getExtraRoomNo());
                System.out.println("Extra Name: " + extraDto.getExtraName());
                System.out.println("Extra Day No: " + extraDto.getExtraDayNo());
                System.out.println("Extra Date Start: " + extraDto.getExtraDateStart());
                System.out.println("Extra Date End: " + extraDto.getExtraDateEnd());
                System.out.println("Extra Rate: " + extraDto.getExtraRate());
                System.out.println("Extra Weekday Rate: " + extraDto.getExtraWeekdayRate());
                System.out.println("Extra Friday Rate: " + extraDto.getExtraFridayRate());
                System.out.println("Extra Saturday Rate: " + extraDto.getExtraSaturdayRate());
                System.out.println("Extra Sunday Rate: " + extraDto.getExtraSundayRate());
                System.out.println("-----------");
            }
        }


        return processExtraRates(extraRateList);
    }


    // BASIC_RATE 요금 수정
    @Override
    public int basicRateUpdate(SellerDto sellerDto, int accommodationNo) {
        List<SellerDto> roomInfoList = mapper.bRoomInfoSearch(sellerDto.getRoomName(), accommodationNo);

        int updateCount = 0;

        for (SellerDto roomInfo : roomInfoList) {
            SellerDto basicRate = new SellerDto();
            basicRate.setRoomNo(roomInfo.getRoomNo());

            // 요일에 맞는 요금을 설정합니다.
            for (int dayNo = 1; dayNo <= 4; dayNo++) {
                switch (dayNo) {
                    case 1:
                        basicRate.setBasicRate(sellerDto.getWeekdayRate());
                        break;
                    case 2:
                        basicRate.setBasicRate(sellerDto.getFridayRate());
                        break;
                    case 3:
                        basicRate.setBasicRate(sellerDto.getSaturdayRate());
                        break;
                    case 4:
                        basicRate.setBasicRate(sellerDto.getSundayRate());
                        break;
                }
                basicRate.setBasicDayNo(dayNo);
                updateCount += mapper.basicRateUpdate(basicRate);
            }
        }
        return updateCount;
    }

    // 추가 요금 업데이트 또는 삽입
    @Override
    public int extraRateUpdate(SellerDto sellerDto, int accommodationNo) {
        int updateCount = 0;
        int insertCount = 0;

        for (SellerDto.ExtraDto extraRate : sellerDto.getExtraRates()) {
            extraRate.setExtraRoomNo(sellerDto.getRoomNo());

            int result = mapper.extraRateUpdate(extraRate);

            if (result == 0) {
                insertCount += mapper.extraRateInsert(extraRate);
            } else {
                updateCount += result;
            }
        }
        return updateCount + insertCount;
    }

    // 요금 처리 로직 (기본 요금은 기존 방식 사용)
    private List<SellerDto> processRates(List<SellerDto> rateList, boolean isExtraRate) {
        Map<String, SellerDto> rateMap = new HashMap<>();

        for (SellerDto dto : rateList) {
            String roomName = dto.getRoomName();
            SellerDto existingDto = rateMap.get(roomName);

            if (existingDto == null) {
                existingDto = new SellerDto();
                existingDto.setRoomName(roomName);
                rateMap.put(roomName, existingDto);
            }

            // 기본 요금
            if (!isExtraRate) {
                switch (dto.getBasicDayNo()) {
                    case 1: existingDto.setWeekdayRate(dto.getBasicRate()); break;
                    case 2: existingDto.setFridayRate(dto.getBasicRate()); break;
                    case 3: existingDto.setSaturdayRate(dto.getBasicRate()); break;
                    case 4: existingDto.setSundayRate(dto.getBasicRate()); break;
                }
            }
        }

        return new ArrayList<>(rateMap.values());
    }

    // 추가 요금 처리 로직 (각 요금 항목을 ExtraDto로 처리)
    private List<SellerDto> processExtraRates(List<SellerDto> rateList) {
        Map<String, SellerDto.ExtraDto> extraRateMap = new HashMap<>();

        for (SellerDto dto : rateList) {
            for (SellerDto.ExtraDto extra : dto.getExtraRates()) {
                String extraKey = extra.getExtraName() + "_" + extra.getExtraDateStart() + "_" + extra.getExtraDateEnd();

                SellerDto.ExtraDto existingExtraDto = extraRateMap.get(extraKey);

                if (existingExtraDto == null) {
                    existingExtraDto = new SellerDto.ExtraDto();
                    existingExtraDto.setExtraName(extra.getExtraName());
                    existingExtraDto.setExtraDateStart(extra.getExtraDateStart());
                    existingExtraDto.setExtraDateEnd(extra.getExtraDateEnd());
                    extraRateMap.put(extraKey, existingExtraDto);
                }

                switch (extra.getExtraDayNo()) {
                    case 1:
                        existingExtraDto.setExtraWeekdayRate(extra.getExtraRate());
                        break;
                    case 2:
                        existingExtraDto.setExtraFridayRate(extra.getExtraRate());
                        break;
                    case 3:
                        existingExtraDto.setExtraSaturdayRate(extra.getExtraRate());
                        break;
                    case 4:
                        existingExtraDto.setExtraSundayRate(extra.getExtraRate());
                        break;
                    default:
                        // Handle any other cases if needed
                        break;
                }
            }
        }

        // 이제 ExtraDto 리스트를 SellerDto에 추가합니다.
        SellerDto resultDto = new SellerDto();
        resultDto.setExtraRates(new ArrayList<>(extraRateMap.values()));

        return Collections.singletonList(resultDto);
    }


}

