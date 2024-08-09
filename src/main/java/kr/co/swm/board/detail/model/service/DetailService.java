package kr.co.swm.board.detail.model.service;

import kr.co.swm.board.detail.model.dto.DetailDto;

import java.util.List;

public interface DetailService {

//   장소
    List<DetailDto> getPlace();

    List<DetailDto> getRate();
}
