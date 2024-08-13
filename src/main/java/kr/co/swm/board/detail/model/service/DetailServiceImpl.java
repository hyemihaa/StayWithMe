package kr.co.swm.board.detail.model.service;

import kr.co.swm.board.detail.model.dto.DetailDto;
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

   @Override
   public List<DetailDto> getPlace() {
       return detailMapper.getPlace();
   }

    @Override
    public DetailDto getPost(Long id) {
        return detailMapper.findPostById(id);
    }

    @Override
    public double getAvgRate(int boardNo) {
        return detailMapper.getAvgRate(boardNo);
    }



}
