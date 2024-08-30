package kr.co.swm.board.detail.controller;


import kr.co.swm.board.detail.model.DTO.DetailDTO;
import kr.co.swm.board.detail.model.service.DetailService;
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

        //  하단 관련 장소
        List<DetailDTO> subPlace = detailService.getSubPlace(boardNo);

        //  게시글 상세 조회
        DetailDTO post = detailService.getPost(boardNo);


        //  부대시설 불러오기
        List<DetailDTO> facilities = detailService.getFacilities(boardNo);

        //  이미지 경로 불러오
//        String imagePath = detailService.getImagePath(boardNo);

        //  데이터 바인딩
        model.addAttribute("place",place);
        model.addAttribute("post",post);
        model.addAttribute("facilities",facilities);
        model.addAttribute("subPlace",subPlace);

        //  각 페이지마다 boardNo에 대한 다른 값 불러오기
        // http://localhost:8080/hotel-single?boardNo=1 이면 boardNo=1
        // http://localhost:8080/hotel-single?boardNo=2 이면 boardNo=2
        model.addAttribute("boardNo", boardNo);

        return "hotel-single"; //templates / ** .html
    }
}
