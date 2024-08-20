package kr.co.swm.model.service;


import kr.co.swm.mappers.SellerMapper;
import kr.co.swm.model.dto.SellerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerMapper mapper;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public SellerServiceImpl(SellerMapper mapper, TransactionTemplate transactionTemplate) {
        this.mapper = mapper;
        this.transactionTemplate = transactionTemplate;
    }

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 업소 조회수
    @Override
    public int roomViews(int accommodationNo) {
        return mapper.roomViews(accommodationNo);
    }

    // 대시보드 정보 조회
    @Override
    public List<SellerDto> mainList(int accommodationNo) {

        List<SellerDto> resultList = mapper.reserveData(accommodationNo);

        // 디버깅용 출력
        System.out.println("Result List Size: " + resultList.size());
        for (SellerDto item : resultList) {
            System.out.println("Reservation Date: " + item.getReservationDate());
            System.out.println("Reservation Type: " + item.getReservationType());
            System.out.println("Reservation Status: " + item.getReservationStatus());
            System.out.println("Reserve Amount: " + item.getReserveAmount());
            System.out.println("------------------------------------------");
        }

        return resultList;
    }

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 예약 검색 조회
    @Override
    public List<SellerDto> reservationSearch(int accommodationNo, String dateType, String startDate, String endDate, String searchKeyword, String reservationStatus) {

        // 입력 값에 대한 유효성 검사
        if (startDate != null && endDate != null && startDate.compareTo(endDate) > 0) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        // 2. 필수 입력값에 대한 기본적인 유효성 검사
        if (accommodationNo <= 0) {
            throw new IllegalArgumentException("Invalid accommodation number");
        }

        List<SellerDto> reservationSearch = mapper.reservationSearch(accommodationNo, dateType, startDate, endDate, searchKeyword, reservationStatus);

        // 디버깅: 검색 결과 출력
        if (reservationSearch != null && !reservationSearch.isEmpty()) {
            for (SellerDto item : reservationSearch) {
                System.out.println("---------- reservationSearch ServiceImpl ----------");
                System.out.println("예약 정보: " + item.getReserveRoomName());
                System.out.println("입실일: " + item.getReserveCheckIn());
                System.out.println("퇴실일: " + item.getReserveCheckOut());
                System.out.println("예약자: " + item.getUserName());
                System.out.println("예약연락처: " + item.getUserPhone());
                System.out.println("결제금액: " + item.getReserveAmount());
                System.out.println("예약상태: " + item.getReservationStatus());
                System.out.println("예약취소일: " + item.getReservationCancellationDate());
                System.out.println("-----------------------------------------");
            }
        } else {
            System.out.println("검색 결과가 없습니다.");
        }

        return reservationSearch;
    }



//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 일별 예약 조회
    @Override
    public List<SellerDto> roomData(int accommodationNo, String selectedDate) {

        List<SellerDto> roomData = mapper.roomData(accommodationNo, selectedDate);

        return roomData;
    }


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□




    // 관리자 보유 객실 조회
    @Override
    public List<SellerDto> accommodationRoomData(int accommodationNo) {
        List<SellerDto> accommodationRoomData = mapper.accommodationRoomData(accommodationNo);

        for(SellerDto item : accommodationRoomData) {
            System.out.println();
            System.out.println("============ Servive AccommodationRoomData ============");
            System.out.println("ROON NO : " + item.getRoomNo());
            System.out.println("ROON NAME : " + item.getRoomName());
            System.out.println("ROON TYPE NAME : " + item.getRoomTypeName());
            System.out.println("=======================================================");
            System.out.println();
        }

        return accommodationRoomData;
    }

    // 관리자 객실 예약 조회
    @Override
    public List<SellerDto> monthlyData(int accommodationNo) {
        List<SellerDto> monthlyData = mapper.monthlyData(accommodationNo);

        for(SellerDto item : monthlyData) {
            System.out.println();
            System.out.println("============ Servive AccommodationRoomData ============");
            System.out.println("ROON NO : " + item.getRoomNo());
            System.out.println("ROON NAME : " + item.getRoomName());
            System.out.println("ROON CHECK IN DATE : " + item.getReserveCheckIn());
            System.out.println("ROON CHECK OUT DATE : " + item.getReserveCheckOut());
            System.out.println("ROON STATUS : " + item.getReservationStatus());
            System.out.println("=======================================================");
            System.out.println();
        }

        return monthlyData;
    }




//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□


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

        // 추가 요금 정보를 콘솔에 출력 (디버깅 목적)
        for (SellerDto sellerDto : extraRateList) {
            System.out.println("Room Name: " + sellerDto.getRoomName());
            List<SellerDto.ExtraDto> extraRates = sellerDto.getExtraRates();
            for (SellerDto.ExtraDto extraDto : extraRates) {
                System.out.println("□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");
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
                System.out.println("□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");
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
            // RoomName과 AccommodationNo를 기반으로 Room 정보를 조회
            List<SellerDto.ExtraDto> roomInfoList = mapper.eRoomInfoSearch(sellerDto.getRoomName(), accommodationNo);

            int updateCount = 0;
            int insertCount = 0;

            // RoomInfoList에서 각 roomInfo를 통해 RoomNo를 설정
            for (SellerDto.ExtraDto roomInfo : roomInfoList) {
                for (SellerDto.ExtraDto extraRate : sellerDto.getExtraRates()) {
                    // DTO 객체 초기화 및 클리닝
                    SellerDto.ExtraDto newExtraRate = initializeNewExtraRate(roomInfo, extraRate);

                    // 각 요일에 대한 EXTRA_RATE 업데이트 또는 삽입
                    for (int dayNo = 1; dayNo <= 4; dayNo++) {
                        setExtraRateForDay(newExtraRate, extraRate, dayNo);

                        // 업데이트에 필요한 데이터가 누락되지 않았는지 확인
                        if (isValidExtraRate(newExtraRate)) {
                            int result = mapper.extraRateUpdate(newExtraRate);
                            if (result == 0) {
                                // 업데이트되지 않은 경우, 새로운 레코드로 삽입
                                insertCount += mapper.extraRateInsert(newExtraRate);
                                System.out.println("새로운 추가 요금 레코드가 삽입되었습니다: " + newExtraRate);
                            } else {
                                // 성공적으로 업데이트된 경우 카운트 증가
                                updateCount += result;
                                System.out.println("추가 요금 레코드가 업데이트되었습니다: " + newExtraRate);
                            }
                        } else {
                            // 필요한 필드가 누락된 경우 처리 (예: 로그를 남기거나 예외를 발생시킴)
                            System.err.println("필수 필드 누락: " + newExtraRate);
                        }
                    }
                }
            }

            // 트랜잭션 자동 커밋은 TransactionTemplate이 처리하므로 별도의 커밋 강제 수행은 필요 없음
            System.out.println("업데이트된 레코드 수: " + updateCount);
            System.out.println("삽입된 레코드 수: " + insertCount);

            return updateCount + insertCount;
    }

    // 새로운 ExtraRate를 초기화하는 메서드
    private SellerDto.ExtraDto initializeNewExtraRate(SellerDto.ExtraDto roomInfo, SellerDto.ExtraDto extraRate) {
        SellerDto.ExtraDto newExtraRate = new SellerDto.ExtraDto();
        newExtraRate.setExtraRoomNo(roomInfo.getExtraRoomNo());
        newExtraRate.setExtraName(extraRate.getExtraName());
        newExtraRate.setExtraDateStart(extraRate.getExtraDateStart());
        newExtraRate.setExtraDateEnd(extraRate.getExtraDateEnd());
        return newExtraRate;
    }

    // 요일에 따른 요금 설정을 하는 메서드
    private void setExtraRateForDay(SellerDto.ExtraDto newExtraRate, SellerDto.ExtraDto extraRate, int dayNo) {
        switch (dayNo) {
            case 1:
                newExtraRate.setExtraRate(extraRate.getExtraWeekdayRate());
                break;
            case 2:
                newExtraRate.setExtraRate(extraRate.getExtraFridayRate());
                break;
            case 3:
                newExtraRate.setExtraRate(extraRate.getExtraSaturdayRate());
                break;
            case 4:
                newExtraRate.setExtraRate(extraRate.getExtraSundayRate());
                break;
        }
        newExtraRate.setExtraDayNo(dayNo);
    }

    // 새로운 ExtraRate의 유효성을 검사하는 메서드
    private boolean isValidExtraRate(SellerDto.ExtraDto extraRate) {
        return extraRate.getExtraName() != null && extraRate.getExtraDateStart() != null && extraRate.getExtraDateEnd() != null;
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
                        // 기타 경우를 처리할 필요가 있을 경우 추가
                        break;
                }
            }
        }

        // 이제 ExtraDto 리스트를 SellerDto에 추가합니다.
        SellerDto resultDto = new SellerDto();
        resultDto.setExtraRates(new ArrayList<>(extraRateMap.values()));

        return Collections.singletonList(resultDto);
    }

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 기간 수정 페이지 정보 조회
    @Override
    public List<SellerDto.ExtraDto> extraSeasonList(int accommodationNo) {
        return mapper.extraSeasonList(accommodationNo);
    }

    // EXTRA NAME 기준으로 데이터 삭제
    @Override
    public boolean extraRateDelete(String extraName, int accommodationNo) {
        return mapper.extraRateDelete(extraName, accommodationNo) > 0;
    }

    // 추가 요금 기간 수정
    @Override
    public boolean extraSeasonUpdate(List<SellerDto.ExtraDto> extraSeasonUpdate, int accommodationNo) {
        // 기존의 추가 요금 정보를 조회
        List<SellerDto.ExtraDto> existingExtraRates = mapper.extraTableSearch(accommodationNo);

        // 조회된 기존 요금 정보 출력
        System.out.println("Existing Extra Rates:");
        for (SellerDto.ExtraDto existingDto : existingExtraRates) {
            System.out.println("ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ");
            System.out.println(" Extra Name: " + existingDto.getExtraName());
            System.out.println(" RoomNo: " + existingDto.getExtraRoomNo());
            System.out.println(" Start: " + existingDto.getExtraDateStart());
            System.out.println(" End: " + existingDto.getExtraDateEnd());
            System.out.println("ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ");
        }

        boolean result = true;

        for (SellerDto.ExtraDto updatedDto : extraSeasonUpdate) {
            for (SellerDto.ExtraDto existingDto : existingExtraRates) {
                // 요금타입 이름이 동일한 경우, 기존 정보의 RoomNo를 사용하여 업데이트
                if (existingDto.getExtraName().equals(updatedDto.getExtraName())) {
                    updatedDto.setExtraRoomNo(existingDto.getExtraRoomNo());
                    updatedDto.setExtraDayNo(existingDto.getExtraDayNo());

                    try {
                        // Update 실행 전 로그
                        System.out.println("Updating: " + updatedDto.getExtraName() + ", Start: " + updatedDto.getExtraDateStart() + ", End: " + updatedDto.getExtraDateEnd() + ", RoomNo: " + updatedDto.getExtraRoomNo());

                        int updateResult = mapper.extraSeasonUpdate(updatedDto);

                        // Update 실행 후 로그 (성공 여부 확인)
                        if (updateResult == 0) {
                            result = false;
                            System.out.println("Update failed for: " + updatedDto.getExtraName());
                            break;
                        } else {
                            System.out.println("Update successful for: " + updatedDto.getExtraName());
                        }
                    } catch (Exception e) {
                        System.out.println("Exception during update: " + e.getMessage());
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;
    }
}




