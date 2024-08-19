package kr.co.swm.board.mapper;

import kr.co.swm.board.detail.model.DTO.DetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DetailMapper {
    public List<DetailDTO> getPlace();

    double getAvgRate(@Param("boardNo") int boardNo);

    DetailDTO getPost(@Param("boardNo") int boardNo);
}
