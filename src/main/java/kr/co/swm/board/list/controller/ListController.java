package kr.co.swm.board.list.controller;

import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.PageInfoDTO;
import kr.co.swm.board.list.model.DTO.SearchDTO;
import kr.co.swm.board.list.model.sevice.ListService;
import kr.co.swm.board.list.util.Pagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String list(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
                       @ModelAttribute SearchDTO searchDTO,
                       Model model) {

        //전체 게시글 수 구하기(Pagenation 영역)
        int listCount = listService.getTotalCount(searchDTO);
        int pageLimit = 3; // 보여질 페이지
        int boardLimit = 5; // 페이지당 게시글

        PageInfoDTO pi = pagenation.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
//        listCount: 전체 항목, currentPage: 현재 페이지

        //장소 불러오기
        List<ListDTO> place = listService.getPlace(pi, searchDTO);

        double rate = 0;
        // 평균 별점 계산
        if(place.size() > 0) {
            rate = listService.getAvgRate(place.get(0).getBoardNo());
        }

        // 최소 기본 가격
        List<ListDTO> cost = listService.getCost();

        //데이터 바인딩
        //장소
        model.addAttribute("place", place);
        //별점 평균
        model.addAttribute("rate", rate);
        // 페이징
        model.addAttribute("pi",pi);
        // 최저 기본 가격
        model.addAttribute("cost", cost);

        model.addAttribute("searchDTO", new SearchDTO());
        return "tour";  // tour위치 반환
        //templates / ** .html

    }

    @PostMapping("/get-list")
    public String getList(Model model,
                          @RequestParam(value="currentPage", defaultValue="1") int currentPage,
                          @ModelAttribute SearchDTO searchDTO) {

        // 전체 게시글 수 구하기(Pagenation 영역)
        int listCount = listService.getListCount(searchDTO);
        int pageLimit = 3; // 보여질 페이지
        int boardLimit = 5; // 페이지당 게시글

        PageInfoDTO pi = pagenation.getPageInfo(listCount, currentPage, pageLimit, boardLimit);

        // 날짜 기준 필터링 된 업소 리스트 조회
        List<ListDTO> place = listService.getList(searchDTO);

        // 최소 기본 가격
        List<ListDTO> cost = listService.getCost();

        // 부가시설 조회
        List<String> uniqueFacilities = listService.getFacilities(searchDTO);

        // 데이터 바인딩
        model.addAttribute("place", place);
        model.addAttribute("pi", pi);
        model.addAttribute("cost", cost);
        model.addAttribute("uniqueFacilities", uniqueFacilities);

        // searchDTO 또는 listDto를 뷰로 전달
        model.addAttribute("searchDTO", searchDTO);

        return "tour";  // 리스트 페이지로 이동
    }

}
