package kr.co.swm.board.detail.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DetailController {

    @GetMapping("/hotel-single")
    public String hotel() {

//        List<DetailDto> posts = detailService.getAllposts();

        return "hotel-single"; //templates / ** .html

    }
//      //목록 불러오기
//      //DetailService 의존성 주입
//      private final DetailService detailService;
//
//      @Autowired
//      public DetailController(DetailService detailService) {
//        this.detailService = detailService;
//      }



}





