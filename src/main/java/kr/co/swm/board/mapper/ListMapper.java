package kr.co.swm.board.mapper;

import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.MainSearchDTO;
import kr.co.swm.board.list.model.DTO.PageInfoDTO;
import kr.co.swm.board.list.model.DTO.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ListMapper {
  //  게시글 불러오기
//  List<ListDTO> getPlace(@Param("pi") PageInfoDTO pi);
  List<ListDTO> getPlace(@Param("pi") PageInfoDTO pi, @Param("searchDTO") SearchDTO searchDTO);
  // 총 게시글 개수
  int getTotalCount(@Param("searchDTO") SearchDTO searchDTO);
  // 별점 평균
  double getAvgRate(@Param("boardNo") int boardNo);
  // 기본 가격
  List<ListDTO> getCost();

  int getListCount(@Param("mainSearchDTO") MainSearchDTO mainSearchDTO);

  List<ListDTO> getList(@Param("mainSearchDTO") MainSearchDTO mainSearchDTO);

  List<String> getFacilities(@Param("mainSearchDTO") MainSearchDTO mainSearchDTO);
}
