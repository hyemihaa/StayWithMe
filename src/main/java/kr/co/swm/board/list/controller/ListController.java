package kr.co.swm.board.list.controller;


import kr.co.swm.board.list.model.dto.ListDto;
import kr.co.swm.board.list.model.dto.PageInfoDto;
import kr.co.swm.board.list.model.sevice.ListService;
import kr.co.swm.board.list.util.Pagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ListController {


    @GetMapping("/tour")    //  tour에 대한 Get요청을 메소드와 mapping시킴
    public String list(@RequestParam(value="currentPage", defaultValue="1") int currentPage, Model model) {

    //전체 게시글 수 구하기(Pagenation 영역)
    int listCount = listService.getTotalCount();
    int pageLimit = 10; // 보여질 페이지
    int boardLimit = 5; // 페이지당 게시글

      PageInfoDto pi = pagenation.getPageInfo(listCount, currentPage, pageLimit, boardLimit);

        //목록 불러오기
        List<ListDto> posts = listService.getAllPosts(pi);


      model.addAttribute("posts",posts);
      model.addAttribute("pi",pi);

        return "tour";  // tour위치 반환
        //templates / ** .html
    }
        //데이터 바인딩



      //ListService 의존성 주입
    private final ListService listService;
      //pagenation 의존성 주입
    private final Pagenation pagenation;

    @Autowired
    public ListController(ListService listService, Pagenation pagenation) {
        this.listService = listService;
        this.pagenation = pagenation;
    }







}
