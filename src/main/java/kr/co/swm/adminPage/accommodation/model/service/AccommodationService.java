package kr.co.swm.adminPage.accommodation.model.service;

import kr.co.swm.adminPage.accommodation.model.dto.AccommodationDto;
import kr.co.swm.adminPage.accommodation.model.dto.AccommodationImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccommodationService {

    /**
     * 업소 저장
     * @param accommodationDto
     * @param mainImage
     * @return
     */
    int saveAccommodation(AccommodationDto accommodationDto, AccommodationImageDto mainImage);

    /**
     * 객실 저장
     * @param accommodationDto
     * @param room
     * @param roomsSize
     * @param subFile
     * @param startIndex
     * @return
     */
    int enrollRooms(AccommodationDto  accommodationDto,
                    AccommodationDto room,
                    int roomsSize,
                    List<MultipartFile> subFile,
                    int startIndex);

}
