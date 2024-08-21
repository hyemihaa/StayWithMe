package kr.co.swm.adminPage.accommodation.model.service;


import kr.co.swm.adminPage.accommodation.mapper.AccommodationMapper;
import kr.co.swm.adminPage.accommodation.model.dto.AccommodationDto;
import kr.co.swm.adminPage.accommodation.model.dto.AccommodationImageDto;
import kr.co.swm.adminPage.accommodation.util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
            for (int i = 0; i < accommodationDto.getAccommodationType().size(); i++) {
                String facility = accommodationDto.getAccommodationType().get(i);
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
}

