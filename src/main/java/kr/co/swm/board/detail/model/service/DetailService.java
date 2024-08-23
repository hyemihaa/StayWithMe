package kr.co.swm.board.detail.model.service;

import kr.co.swm.board.detail.model.DTO.DetailDTO;

import java.util.List;

public interface DetailService {

//   장소
    List<DetailDTO> getPlace();
//  게시글 상세조회
    DetailDTO getPost(int boardNo);
//  평균 별점
    double getAvgRate(int boardNo);

//  방 평균 별점
    double getRate(int boardNo);
    
//  하단 관련 항목
//    List<DetailDTO> getSubPlace();
}
