package kr.co.swm.board.detail.model.service;

import kr.co.swm.board.detail.model.DTO.DetailDTO;
import kr.co.swm.board.mapper.DetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailServiceImpl implements DetailService{

      //Mapper 의존성 주입
    private final DetailMapper detailMapper;

    @Autowired
    public DetailServiceImpl(DetailMapper detailMapper){
        this.detailMapper = detailMapper;
    }

    //  장소 불러오기
   @Override
   public List<DetailDTO> getPlace() {
       return detailMapper.getPlace();
   }

   //   게시글 상세정보 조회
    @Override
    public DetailDTO getPost(int boardNo) {
        return detailMapper.getPost(boardNo);
    }

    //  업체 평균 별점 불러오기
    @Override
    public double getAvgRate(int boardNo) {
        return detailMapper.getAvgRate(boardNo);
    }

    //  금액
//    @Override
//    public List<DetailDTO> getCost() {
//        return detailMapper.getCost();
//    }

    // 방 평균 별점
//    @Override
//    public double getRoomRate(int boardNo) {
//        return detailMapper.getRoomRate(boardNo);
//    }


}
