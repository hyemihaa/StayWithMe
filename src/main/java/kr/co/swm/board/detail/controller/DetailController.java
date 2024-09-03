package kr.co.swm.board.detail.controller;


import kr.co.swm.adminPage.accommodation.model.dto.AccommodationImageDto;
import kr.co.swm.board.detail.model.DTO.DetailDTO;
import kr.co.swm.board.detail.model.service.DetailService;
import kr.co.swm.board.list.model.DTO.SearchDTO;
import kr.co.swm.model.dto.SellerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
                         @RequestParam(value = "checkInDate", required = false) String checkInDate,
                         @RequestParam(value = "checkOutDate", required = false) String checkOutDate,
                         SearchDTO searchDto,
                         Model model) {



        // 몇박 개수 구하기
        LocalDate startDates = LocalDate.parse(checkInDate);
        LocalDate endDates = LocalDate.parse(checkOutDate);
        long nights = calculateNights(startDates, endDates);

        List<DetailDTO> mainImages = detailService.getImages(boardNo);


        //  장소 불러오기
        List<DetailDTO> place = detailService.getPlace(boardNo, nights);

        //  하단 관련 장소
        List<DetailDTO> subPlace = detailService.getSubPlace(boardNo);


        //  게시글 상세 조회
        DetailDTO post = detailService.getPost(boardNo);



        // 날짜에 대한 주중,금,토,일 구분
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate startDate = LocalDate.parse(searchDto.getCheckInDate(), formatter);
//        LocalDate endDate = LocalDate.parse(searchDto.getCheckOutDate(), formatter);

        //  부대시설 불러오기
        List<DetailDTO> facilities = detailService.getFacilities(boardNo);

//        List<DetailDTO> roomImage = new ArrayList<>();
//        int beforeRoomNo = 0;
//        int roomNo = 0;
//        for(DetailDTO item : place) {
//            roomNo = item.getRoomNo();
//            if(beforeRoomNo != roomNo) {
//                roomImage.add(item);
//                beforeRoomNo = roomNo;
//            }
//        }

        //  데이터 바인딩
        model.addAttribute("place", place);
        model.addAttribute("post", post);
        model.addAttribute("facilities", facilities);
        model.addAttribute("subPlace", subPlace);
        model.addAttribute("images", mainImages);
//        model.addAttribute("roomImage", roomImages);

        //  각 페이지마다 boardNo에 대한 다른 값 불러오기
        // http://localhost:8080/hotel-single?boardNo=1 이면 boardNo=1
        // http://localhost:8080/hotel-single?boardNo=2 이면 boardNo=2
        model.addAttribute("boardNo", boardNo);

        return "hotel-single"; //templates / ** .html
    }
    public static long calculateNights(LocalDate checkInDate, LocalDate checkOutDate) {
        // 퇴실일에서 입실일을 빼서 숙박일 계산
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
}
