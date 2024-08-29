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
   public List<DetailDTO> getPlace(int boardNo) {
       List<DetailDTO> list = detailMapper.getPlace(boardNo);
       for(DetailDTO item : list) {
           System.out.println("========== ServiceImpl Detail List ==========");
           System.out.println("Board Name : " + item.getBoardName());
           System.out.println("Board Room Type : " + item.getBoardRoomType());
           System.out.println("Board Room Type : " + item.getBoardCount());
           System.out.println("Board Check In : " + item.getBoardCheckIn());
           System.out.println("Board Min Person : " + item.getBoardMinPerson());
           System.out.println("Board Max Person : " + item.getBoardMaxPerson());
           System.out.println("Board Address : " + item.getBoardAddress());
           System.out.println("Lat : " + item.getLat());
           System.out.println("Lon : " + item.getLon());
           System.out.println("=============================================");
       }
       return list;
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

    // 방 평균 별점
    @Override
    public double getRate(int boardNo) {
      return detailMapper.getRate(boardNo);
    }

    @Override
    public List<DetailDTO> getFacilities(int boardNo) {
        return detailMapper.getFacilities(boardNo);
    }

    // 하단 관련 항목
    @Override
    public List<DetailDTO> getSubPlace(int boardNo) {
        return detailMapper.getSubPlace(boardNo);
    }


}
