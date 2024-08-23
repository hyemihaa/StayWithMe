package kr.co.swm.board.detail.controller;


import kr.co.swm.board.detail.model.DTO.DetailDTO;
import kr.co.swm.board.detail.model.service.DetailService;
import kr.co.swm.board.list.model.DTO.ListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DetailController {

    //DetailService 의존성 주입
      private final DetailService detailService;

      @Autowired
      public DetailController(DetailService detailService) {
          this.detailService = detailService;
      }

    @GetMapping("/hotel-single")
    public String detail(@RequestParam("boardNo") int boardNo,
                         Model model) {

        System.out.println(boardNo);

        //  장소 불러오기
        List<DetailDTO> place = detailService.getPlace(boardNo);

        for(DetailDTO item : place) {
            System.out.println("========== Controller Detail List ==========");
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


        //  하단 관련 장소
//      List<DetailDTO> subPlace = detailService.getSubPlace();

        //  게시글 상세 조회
        DetailDTO post = detailService.getPost(boardNo);

        //  별점 평균
        double avgRate = detailService.getAvgRate(boardNo);


        //  방 평균 점수
        double rate = detailService.getRate(boardNo);



        //  데이터 바인딩
        model.addAttribute("place",place);
        model.addAttribute("post",post);
        model.addAttribute("avgRate",avgRate);
        model.addAttribute("rate",rate);
//        model.addAttribute("subPlace",subPlace);

        //  각 페이지마다 boardNo에 대한 다른 값 불러오기
        // http://localhost:8080/hotel-single?boardNo=1 이면 boardNo=1
        // http://localhost:8080/hotel-single?boardNo=2 이면 boardNo=2
        model.addAttribute("boardNo", boardNo);

        return "hotel-single"; //templates / ** .html
    }
}
