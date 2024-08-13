package kr.co.swm.board.detail.controller;


import kr.co.swm.board.detail.model.dto.DetailDto;
import kr.co.swm.board.detail.model.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DetailController {

    @GetMapping("/hotel-single/{id}")
    public String detail(@PathVariable Long id, Model model) {

        // 장소 불러오기
        List<DetailDto> place = detailService.getPlace();

        // 게시글 상세 조회
        DetailDto post = detailService.getPost(id);

        //별점
        double rate = detailService.getAvgRate(post.getBoardNo());

        model.addAttribute("place",place);
        model.addAttribute("post",post);
        model.addAttribute("rate",rate);


        return "hotel-single"; //templates / ** .html

    }
      //목록 불러오기
      //DetailService 의존성 주입
      private final DetailService detailService;

      @Autowired
      public DetailController(DetailService detailService) {
          this.detailService = detailService;
      }



}





