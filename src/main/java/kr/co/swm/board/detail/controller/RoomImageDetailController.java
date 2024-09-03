package kr.co.swm.board.detail.controller;


import kr.co.swm.board.detail.model.DTO.DetailDTO;
import kr.co.swm.board.detail.model.service.DetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomImageDetailController {


    private final DetailService detailService;


    @GetMapping("/getRoomImages")
    public ResponseEntity<List<DetailDTO>> getRoomImages(@RequestParam("roomNo") int roomNo) {

        // roomNo에 해당하는 이미지 URL 리스트를 반환합니다.
        List<DetailDTO> imageUrls = detailService.getRoomImages(roomNo);

        return new ResponseEntity<>(imageUrls, HttpStatus.OK);
    }
}
