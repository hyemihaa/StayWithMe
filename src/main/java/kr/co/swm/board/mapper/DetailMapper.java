package kr.co.swm.board.mapper;

import kr.co.swm.adminPage.accommodation.model.dto.AccommodationImageDto;
import kr.co.swm.board.detail.model.DTO.DetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DetailMapper {

    // 게시글 정보 불러오기
    List<DetailDTO> getPlace(@Param("boardNo") int boardNo,
                             @Param("nights") long nights,
                             @Param("checkInDate")String checkInDate,
                             @Param("checkOutDate")String checkOutDate);

    // 게시글 상세 정보 불러오기
    DetailDTO getPost(@Param("boardNo") int boardNo);

    double getRate(int boardNo);

    List<DetailDTO> getSubPlace(@Param("boardNo") int boardNo);

    List<DetailDTO> getFacilities(int boardNo);

    List<DetailDTO> getMainImages(@Param("boardNo") int boardNo);

    List<DetailDTO> getRoomImages(@Param("roomNo") int roomNo);
}
