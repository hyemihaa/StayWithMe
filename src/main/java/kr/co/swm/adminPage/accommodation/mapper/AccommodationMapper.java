package kr.co.swm.adminPage.accommodation.mapper;

import kr.co.swm.adminPage.accommodation.model.dto.AccommodationDto;
import kr.co.swm.adminPage.accommodation.model.dto.AccommodationImageDto;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.dto.WebDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccommodationMapper {

    int enrollAccommodation(@Param("accommodation") AccommodationDto accommodationDto, @Param("no") Long no);

    int enrollFacilities(@Param("facility") String facility, @Param("no") Long no);

    int enrollMainImage(@Param("mainImage") AccommodationImageDto mainImage, @Param("no") Long no);

    int enrollRoom(@Param("accommodation") AccommodationDto accommodationDto, @Param("no") Long accommodationAdminNo, @Param("categoryNo") int categoryNo, @Param("roomName") String roomName, @Param("checkIn") String checkIn, @Param("checkOut") String checkOut);

    int enrollRoomImages(@Param("subImage") AccommodationImageDto subImage, @Param("roomNo") int roomNo);

    void enrollWeekdayRate(@Param("roomRate") AccommodationDto roomRate, @Param("roomNo") int roomNo, @Param("no") Long accommodationAdminNo);
    void enrollFridayRate(@Param("roomRate") AccommodationDto roomRate, @Param("roomNo") int roomNo, @Param("no") Long accommodationAdminNo);
    void enrollSaturdayRate(@Param("roomRate") AccommodationDto roomRate, @Param("roomNo") int roomNo, @Param("no") Long accommodationAdminNo);
    int enrollSundayRate(@Param("roomRate") AccommodationDto roomRate, @Param("roomNo") int roomNo, @Param("no") Long accommodationAdminNo);

    SellerDto accommodationList(@Param("sellerKey")Long sellerId);

    List<SellerDto> roomsList(@Param("sellerKey")Long sellerId);

    List<SellerDto> getRates(@Param("roomNo")int roomNo);

    List<String> getFacilities(@Param("sellerKey")Long sellerKey);

}
