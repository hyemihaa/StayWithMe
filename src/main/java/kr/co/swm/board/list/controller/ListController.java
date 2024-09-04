package kr.co.swm.board.list.controller;

import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.MainSearchDTO;
import kr.co.swm.board.list.model.sevice.ListService;
import kr.co.swm.board.list.util.Pagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring6.SpringTemplateEngine;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin
public class ListController {

    private final ListService listService;
    private final Pagenation pagenation;
    private final SpringTemplateEngine templateEngine;



    @Autowired
    public ListController(ListService listService, Pagenation pagenation, SpringTemplateEngine templateEngine) {
        this.listService = listService;
        this.pagenation = pagenation;
        this.templateEngine = templateEngine;
    }


//    @GetMapping("/tour")    //  tour에 대한 Get요청을 메소드와 mapping시킴
//    public String list(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
//                       @ModelAttribute SearchDTO searchDTO,
//                       Model model) {
//
//        System.out.println("Type : " + searchDTO.getType());
//        System.out.println("MinRate : " + searchDTO.getMinRate());
//        System.out.println("MaxRate : " + searchDTO.getMaxRate());
//        System.out.println("facilityName : " + searchDTO.getOptions());
//
//
//
//        //전체 게시글 수 구하기(Pagenation 영역)
//        int listCount = listService.getTotalCount(searchDTO);
//        int pageLimit = 3; // 보여질 페이지
//        int boardLimit = 5; // 페이지당 게시글
//
//
//
//        System.out.println("listCount : "  + listCount);
//        System.out.println("currentPage : " + currentPage );
//        System.out.println("pageLimit : " + pageLimit);
//        System.out.println("boardLimit : " + boardLimit);
//
//
//        // 페이징
//        PageInfoDTO pi = pagenation.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
//        //listCount: 전체 항목, currentPage: 현재 페이지
//        System.out.println(pi.getOffset());
//        System.out.println("start page :" + pi.getStartPage());
//        System.out.println("end page :" + pi.getEndPage());
//
//        List<ListDTO> place = listService.getPlace(pi, searchDTO);
//        System.out.println(place.size());
//
//        // 최소 기본 가격
//        List<ListDTO> cost = listService.getCost();
//
//        List<String> uniqueFacilities = listService.getUniqueFacilities();
//
//        //데이터 바인딩
//        // 장소
//        model.addAttribute("place", place);
//        // 페이징
//        model.addAttribute("pi",pi);
//        // 최저 기본 가격
//        model.addAttribute("cost", cost);
//        // 검색
//        model.addAttribute("searchDTO", new SearchDTO());
//
//        model.addAttribute("uniqueFacilities", uniqueFacilities);
//        // 체크인&아웃
////        model.addAttribute("check",check);
//
//
//        return "tour";  // tour위치 반환
//    }





//    @PostMapping("/get-list")
//    public String getList(Model model,
//                          @RequestParam(value="currentPage", defaultValue="1") int currentPage,
//                          @ModelAttribute MainSearchDTO mainSearchDTO,
//                          RedirectAttributes redirectAttributes) {
//
//        System.out.println("Controller type : " + mainSearchDTO.getType());
//
//        // 전체 게시글 수 구하기(Pagenation 영역)
//        int listCount = listService.getListCount(mainSearchDTO);
//        int pageLimit = 3; // 보여질 페이지
//        int boardLimit = 5; // 페이지당 게시글
//
//        PageInfoDTO pi = pagenation.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
//
//        // 날짜 기준 필터링 된 업소 리스트 조회
//        List<ListDTO> place = listService.getList(mainSearchDTO);
//
//        // 최소 기본 가격
//        List<ListDTO> cost = listService.getCost();
//
//        // 부가시설 조회
//        List<String> uniqueFacilities = listService.getFacilities(mainSearchDTO);
//
//        // 데이터 바인딩
//        model.addAttribute("place", place);
//        model.addAttribute("pi", pi);
//        model.addAttribute("cost", cost);
//        model.addAttribute("uniqueFacilities", uniqueFacilities);
//
//        System.out.println("Unique Facilities: " + uniqueFacilities);
//        System.out.println("getType : " + mainSearchDTO.getType());
//
//        // Flash Attributes로 검색 조건 및 현재 페이지 전달
//        redirectAttributes.addFlashAttribute("mainSearchDTO", mainSearchDTO);
//        redirectAttributes.addFlashAttribute("currentPage", currentPage);
//
//        // 리디렉션으로 GET 요청을 처리
//        return "tour";
//    }

//    @PostMapping("/get-list")
//    public String getList(Model model,
//                          @RequestParam(value="currentPage", defaultValue="1") int currentPage,
//                          @ModelAttribute MainSearchDTO mainSearchDTO) {
//
//        // 한번에 로드할 게시물 수 설정
//        int boardLimit = 10;
//
//        // 오프셋 계산
//        int offset = (currentPage - 1) * boardLimit;
//
//        // 날짜 기준 필터링 된 업소 리스트 조회
//        List<ListDTO> place = listService.getList(mainSearchDTO, boardLimit, offset);
//
//        // 부가시설 조회
//        List<String> uniqueFacilities = listService.getFacilities(mainSearchDTO);
//
//        // 데이터 바인딩
//        model.addAttribute("place", place);
//        model.addAttribute("uniqueFacilities", uniqueFacilities);
//        model.addAttribute("mainSearchDTO", mainSearchDTO);
//
//        // tour.html 템플릿을 렌더링하고 결과를 반환
//        return "tour";
//    }
//
//    @RequestMapping(value = "/get-place-items", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
//    public ResponseEntity<List<ListDTO>> getPlaceItems(@RequestParam("currentPage") int currentPage,
//                                                       @ModelAttribute MainSearchDTO mainSearchDTO) {
//
//        // 콘솔에 AJAX 요청 데이터 로그 출력
//        System.out.println("AJAX type : " + mainSearchDTO.getType());
//        System.out.println("AJAX minRate : " + mainSearchDTO.getMinRate());
//        System.out.println("AJAX maxRate : " + mainSearchDTO.getMaxRate());
//        System.out.println("AJAX options : " + mainSearchDTO.getOptions());
//        System.out.println("AJAX mainSearch : " + mainSearchDTO.getMainSearch());
//        System.out.println("AJAX personnel : " + mainSearchDTO.getPersonnel());
//        System.out.println("AJAX checkInDate : " + mainSearchDTO.getCheckInDate());
//        System.out.println("AJAX checkOutDate : " + mainSearchDTO.getCheckOutDate());
//
//        // 한번에 로드할 게시물 수 설정
//        int boardLimit = 10;
//
//        // 오프셋 계산
//        int offset = (currentPage - 1) * boardLimit;
//
//        // 날짜 기준 필터링 된 업소 리스트 조회
//        List<ListDTO> place = listService.getList(mainSearchDTO, boardLimit, offset);
//
//        // JSON으로 데이터 반환
//        return ResponseEntity.ok(place);
//    }
//
//    // GET 요청만 처리하는 메서드 (뒤로가기로 페이지 복귀 시 사용)
//    @GetMapping("/get-list")
//    public String getListForGet(Model model,
//                                @RequestParam(value="currentPage", defaultValue="1") int currentPage,
//                                @ModelAttribute MainSearchDTO mainSearchDTO) {
//
//        // 기존 POST 메서드와 동일하게 처리
//        return getList(model, currentPage, mainSearchDTO);
//    }

    @GetMapping("/get-list")
    public String getList(Model model,
                          @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                          @ModelAttribute MainSearchDTO mainSearchDTO) {

        System.out.println("=============== Controller Main SearchDTO ===============");

        // String 타입의 날짜를 LocalDate로 변환할 필요가 없으므로 Optional로 감싸기만 함
        Optional<LocalDate> checkInDate = Optional.ofNullable(mainSearchDTO.getCheckInDate());
        Optional<LocalDate> checkOutDate = Optional.ofNullable(mainSearchDTO.getCheckOutDate());

        System.out.println("Check In : " + checkInDate.orElse(null));
        System.out.println("Check Out : " + checkOutDate.orElse(null));
        System.out.println("Personnel : " + mainSearchDTO.getPersonnel());
        System.out.println("Search Text : " + Optional.ofNullable(mainSearchDTO.getMainSearch()).orElse("N/A"));
        System.out.println("==========================================================");

        // 날짜 필드의 유효성 검사
        if (checkInDate.isEmpty() || checkOutDate.isEmpty()) {
            model.addAttribute("errorMessage", "Check-in and check-out dates are required.");
            return "error"; // 에러 페이지로 리다이렉트
        }

        // 한번에 로드할 게시물 수 설정
        int boardLimit = 10;

        // 오프셋 계산
        int offset = (currentPage - 1) * boardLimit;

        // 날짜 기준 필터링 된 업소 리스트 조회
        List<ListDTO> place = listService.getList(mainSearchDTO, boardLimit, offset);

        // 디버그용 로그: 조회된 이미지 파일 출력
        if (place.isEmpty()) {
            System.out.println("No places found matching the search criteria.");
        } else {
            for (ListDTO item : place) {
                System.out.println("Image : " + item.getFileName());
            }
        }

        // 부가시설 조회
        List<String> uniqueFacilities = listService.getFacilities(mainSearchDTO);

        // 데이터 바인딩
        model.addAttribute("placeList", place);
        model.addAttribute("uniqueFacilities", uniqueFacilities);
        model.addAttribute("mainSearchDTO", mainSearchDTO);

        // tour.html 템플릿을 렌더링하고 결과를 반환
        return "tour";
    }

    // AJAX 요청을 처리하여 JSON 데이터를 반환하는 메서드 추가
    @GetMapping("/get-place-items")
    @ResponseBody
    public ResponseEntity<List<ListDTO>> getPlaceItems(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
                                                       @ModelAttribute MainSearchDTO mainSearchDTO) {


        try {
            // 한번에 로드할 게시물 수 설정
            int boardLimit = 10;

            // 오프셋 계산
            int offset = (currentPage - 1) * boardLimit;

            // 날짜 기준 필터링 된 업소 리스트 조회
            List<ListDTO> place = listService.getList(mainSearchDTO, boardLimit, offset);

            // JSON으로 데이터 반환
            return ResponseEntity.ok(place);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

