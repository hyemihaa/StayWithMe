package kr.co.swm.board.list.controller;

import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.MainSearchDTO;
import kr.co.swm.board.list.model.DTO.PageInfoDTO;
import kr.co.swm.board.list.model.DTO.SearchDTO;
import kr.co.swm.board.list.model.sevice.ListService;
import kr.co.swm.board.list.util.Pagenation;
import org.springframework.beans.factory.annotation.Autowired;
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
//        this.resourceLoader;
    }

    @GetMapping("/tour")    //  tour에 대한 Get요청을 메소드와 mapping시킴
    public String list(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
                       @ModelAttribute SearchDTO searchDTO,
                       Model model) {

        System.out.println("Type : " + searchDTO.getType());
        System.out.println("MinRate : " + searchDTO.getMinRate());
        System.out.println("MaxRate : " + searchDTO.getMaxRate());
        System.out.println("facilityName : " + searchDTO.getOptions());



        //전체 게시글 수 구하기(Pagenation 영역)
        int listCount = listService.getTotalCount(searchDTO);
        int pageLimit = 3; // 보여질 페이지
        int boardLimit = 5; // 페이지당 게시글



        System.out.println("listCount : "  + listCount);
        System.out.println("currentPage : " + currentPage );
        System.out.println("pageLimit : " + pageLimit);
        System.out.println("boardLimit : " + boardLimit);


        // 페이징
        PageInfoDTO pi = pagenation.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
        //listCount: 전체 항목, currentPage: 현재 페이지
        System.out.println(pi.getOffset());
        System.out.println("start page :" + pi.getStartPage());
        System.out.println("end page :" + pi.getEndPage());

        List<ListDTO> place = listService.getPlace(pi, searchDTO);
        System.out.println(place.size());

        // 최소 기본 가격
        List<ListDTO> cost = listService.getCost();

        List<String> uniqueFacilities = listService.getUniqueFacilities();

        //데이터 바인딩
        // 장소
        model.addAttribute("place", place);
        // 페이징
        model.addAttribute("pi",pi);
        // 최저 기본 가격
        model.addAttribute("cost", cost);
        // 검색
        model.addAttribute("searchDTO", new SearchDTO());

        model.addAttribute("uniqueFacilities", uniqueFacilities);
        // 체크인&아웃
//        model.addAttribute("check",check);


        return "tour";  // tour위치 반환
        //templates / ** .html
    }





    @PostMapping("/get-list")
    public String getList(Model model,
                          @RequestParam(value="currentPage", defaultValue="1") int currentPage,
                          @ModelAttribute MainSearchDTO mainSearchDTO) {

        System.out.println("========== Controller mainSearch ==========");
        System.out.println(mainSearchDTO.getMainSearch());
        System.out.println(mainSearchDTO.getCheckInDate());
        System.out.println(mainSearchDTO.getCheckOutDate());
        System.out.println("===========================================");










        // 필드 이름 수정
        System.out.println("BoardType : " + mainSearchDTO.getType());

        // 전체 게시글 수 구하기(Pagenation 영역)
        int listCount = listService.getListCount(mainSearchDTO);
        int pageLimit = 3; // 보여질 페이지
        int boardLimit = 5; // 페이지당 게시글

        PageInfoDTO pi = pagenation.getPageInfo(listCount, currentPage, pageLimit, boardLimit);

        // 날짜 기준 필터링 된 업소 리스트 조회
        List<ListDTO> place = listService.getList(mainSearchDTO);

        // 최소 기본 가격
        List<ListDTO> cost = listService.getCost();

        // 부가시설 조회
        List<String> uniqueFacilities = listService.getFacilities(mainSearchDTO);

        // 데이터 바인딩
        model.addAttribute("place", place);
        model.addAttribute("pi", pi);
        model.addAttribute("cost", cost);
        model.addAttribute("uniqueFacilities", uniqueFacilities);

        // searchDTO 또는 listDto를 뷰로 전달
        model.addAttribute("searchDTO", mainSearchDTO);

        return "tour";  // 리스트 페이지로 이동
    }


}

