package kr.co.swm.adminPage.accommodation.model.service;

import kr.co.swm.adminPage.accommodation.model.dto.AccommodationDto;
import kr.co.swm.adminPage.accommodation.model.dto.AccommodationImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccommodationService {

    int saveAccommodation(AccommodationDto accommodationDto, AccommodationImageDto mainImage);

    int enrollRooms(AccommodationDto  accommodationDto, String roomCategory, String roomName, String checkIn,
                    String checkOut, int roomValue, AccommodationDto roomRate, int roomsSize, int endIndex, List<MultipartFile> subFile, int startIndex);

}
