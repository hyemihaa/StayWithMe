package kr.co.swm.board.detail.model.service;

import kr.co.swm.board.detail.model.dto.DetailDto;

import java.util.List;

public interface DetailService {

//   장소
    List<DetailDto> getPlace();
//  게시글 상세조회
    DetailDto getPost(Long id);
//  별점
    double getAvgRate(int boardNo);

}
