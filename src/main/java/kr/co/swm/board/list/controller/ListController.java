package kr.co.swm.board.list.controller;

import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.PageInfoDTO;
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

      //ListService 의존성 주입
    private final ListService listService;
      //pagenation 의존성 주입
    private final Pagenation pagenation;

    @Autowired
    public ListController(ListService listService, Pagenation pagenation) {
        this.listService = listService;
        this.pagenation = pagenation;
    }

    @GetMapping("/tour")    //  tour에 대한 Get요청을 메소드와 mapping시킴
    public String list(@RequestParam(value="currentPage", defaultValue="1") int currentPage, Model model) {

    //전체 게시글 수 구하기(Pagenation 영역)
    int listCount = listService.getTotalCount();
    int pageLimit = 3; // 보여질 페이지
    int boardLimit = 5; // 페이지당 게시글

      PageInfoDTO pi = pagenation.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
//        listCount: 전체 항목, currentPage: 현재 페이지

        //장소 불러오기
        List<ListDTO> place = listService.getPlace(pi);


        // 평균 별점 계산
        double rate = listService.getAvgRate(place.get(0).getBoardNo());

        //데이터 바인딩
        //장소
      model.addAttribute("place", place);
        //별점 평균
      model.addAttribute("rate", rate);
        // 페이징
      model.addAttribute("pi",pi);
        // 검색
        return "tour";  // tour위치 반환
        //templates / ** .html

    }










}
