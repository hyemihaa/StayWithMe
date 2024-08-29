package kr.co.swm.adminPage.accommodation.model.service;


import kr.co.swm.adminPage.accommodation.mapper.AccommodationMapper;
import kr.co.swm.adminPage.accommodation.model.dto.AccommodationDto;
import kr.co.swm.adminPage.accommodation.model.dto.AccommodationImageDto;
import kr.co.swm.adminPage.accommodation.util.UploadFile;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.dto.WebDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationMapper mapper;
    private final UploadFile uploadFile;

    @Autowired
    public AccommodationServiceImpl(UploadFile uploadFile, AccommodationMapper mapper) {
        this.uploadFile = uploadFile;
        this.mapper = mapper;
    }

    @Override
    public int saveAccommodation(AccommodationDto accommodationDto, AccommodationImageDto mainImage) {

        int no = accommodationDto.getAcAdminNo();   // 임시 업소관리자 번호

        int result = mapper.enrollAccommodation(accommodationDto, no);
        if (result == 1) {
            for (int i = 0; i < accommodationDto.getAccommodationFacilities().size(); i++) {
                String facility = accommodationDto.getAccommodationFacilities().get(i);
                mapper.enrollFacilities(facility, no);
            }
            return mapper.enrollMainImage(mainImage, no);
        } else {
            return 0;
        }
    }

    @Override
    public int enrollRooms(AccommodationDto accommodationDto,
                           AccommodationDto room,
                           int roomsSize,
                           List<MultipartFile> subFile,
                           int startIndex) {
        int result = 0;     // return 값
        int categoryNo = 0;     // db 데이터에 맞추기 위한 변수 초기화
        int no = accommodationDto.getAcAdminNo();   // 임시 업소 관리자 번호
        categoryNo = room.getCategoryNo(room, categoryNo);

        // 객실 추가
        for (int i = 1; i <= room.getRoomValues(); i++) {
            // 객식 인입
            int enrollRoom = mapper.enrollRoom(accommodationDto,
                    no,
                    categoryNo,
                    room.getRoomName(),
                    room.getCheckInTime(),
                    room.getCheckOutTime());

            int roomNo = accommodationDto.getRoomNo();      // 객실 인입될 때 만들어진 객실 번호

            if (enrollRoom != 1) {
                return result;
            }

            // C 객실별 금액 인입
            mapper.enrollWeekdayRate(accommodationDto, roomNo, no);
            mapper.enrollFridayRate(accommodationDto, roomNo, no);
            mapper.enrollSaturdayRate(accommodationDto, roomNo, no);
            result = mapper.enrollSundayRate(accommodationDto, roomNo, no);

            // C 객실별 다중 이미지 인입
            for (int k = startIndex; k <= room.getEndIndex() + roomsSize - 1; k++) {
                AccommodationImageDto subImage = uploadFile.uploadSingleFile(subFile.get(k), "PREVIEW");
                mapper.enrollRoomImages(subImage, roomNo);
            }
        }
        return result;
    }

    @Override
    public SellerDto accommodationList(Long sellerId) {
        return mapper.accommodationList(sellerId);
    }

    @Override
    public List<SellerDto> roomsList(Long sellerId, SellerDto sellerDto) {


        // SellerDto 리스트를 가져옵니다.
        List<SellerDto> rooms = mapper.roomsList(sellerId);


        // 각 SellerDto 객체에 대해 rates 데이터를 처리합니다.
        for (SellerDto room : rooms) {
            int roomNo = room.getRoomNo();
            // rates 리스트를 가져옵니다.
            List<SellerDto> rates = mapper.getRates(roomNo);
            for (SellerDto rate : rates) {

                int dayNo = rate.getBasicDayNo();
                int rateValue = rate.getBasicRate();
                // 예시로 DAY_NO 값이 1인 경우, weekday 변수에 RATE 값을 저장합니다.
                if (dayNo == 1) {
                    room.setWeekdayRate(rateValue);
                    // 다른 로직이 필요한 경우 추가 구현
                } else if (dayNo == 2) {
                    room.setFridayRate(rateValue);
                } else if (dayNo == 3) {
                    room.setSaturdayRate(rateValue);
                } else if (dayNo == 4) {
                    room.setSundayRate(rateValue);
                }
            }
        }
        // 최종적으로 SellerDto 리스트를 반환합니다.
        return rooms;
    }

    @Override
    public List<String> facilitiesList(Long sellerKey) {
        return mapper.getFacilities(sellerKey);
    }
}

