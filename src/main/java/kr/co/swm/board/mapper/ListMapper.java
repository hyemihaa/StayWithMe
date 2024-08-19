package kr.co.swm.board.mapper;

import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.PageInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ListMapper {
//  게시글 불러오기
  List<ListDTO> getPlace(@Param("pi") PageInfoDTO pi);
// 총 게시글 개수
  int getTotalCount();
// 별점 평균
  double getAvgRate(@Param("boardNo") int boardNo);
}
