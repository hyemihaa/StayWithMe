package kr.co.swm.board.detail.model.service;

import kr.co.swm.board.detail.model.DTO.DetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DetailService {

//   장소
    List<DetailDTO> getPlace(int boardNo, long nights);
//  게시글 상세조회
    DetailDTO getPost(int boardNo);
//  부대시설 불러오기
    List<DetailDTO> getFacilities(int boardNo);

//  하단 관련 항목
    List<DetailDTO> getSubPlace(int boardNo);
}
