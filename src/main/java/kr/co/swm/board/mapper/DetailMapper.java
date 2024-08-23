package kr.co.swm.board.mapper;

import kr.co.swm.board.detail.model.DTO.DetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DetailMapper {
    // 게시글 정보 불러오기
    List<DetailDTO> getPlace(@Param("boardNo") int boardNo);
    // 평균 점수
    double getAvgRate(@Param("boardNo") int boardNo);
    // 게시글 상세 정보 불러오기
    DetailDTO getPost(@Param("boardNo") int boardNo);


    double getRate(int boardNo);

    List<DetailDTO> getSubPlace();
}
