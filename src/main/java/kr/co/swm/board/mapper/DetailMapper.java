package kr.co.swm.board.mapper;

import kr.co.swm.board.detail.model.dto.DetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DetailMapper {
    public List<DetailDto> getPlace();

    double getAvgRate(@Param("boardNo") int boardNo);

    DetailDto getPost(@Param("boardNo") int boardNo);
}
