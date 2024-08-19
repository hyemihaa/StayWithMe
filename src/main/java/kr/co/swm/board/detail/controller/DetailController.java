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
    public String detail(@RequestParam("boardNo") String boardNo, Model model) {

        //  장소 불러오기
        List<DetailDTO> place = detailService.getPlace();

        //  게시글 상세 조회
        DetailDTO post = detailService.getPost(Integer.parseInt(boardNo));

        //  별점 평균
        double rate = detailService.getAvgRate(post.getBoardNo());

        //  데이터 바인딩
        model.addAttribute("place",place);
        model.addAttribute("post",post);
        model.addAttribute("rate",rate);


        return "hotel-single"; //templates / ** .html
    }
}
