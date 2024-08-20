package kr.co.swm.adminPage.accommodation.controller;


import kr.co.swm.adminPage.accommodation.model.dto.AccommodationDto;
import kr.co.swm.adminPage.accommodation.model.dto.AccommodationImageDto;
import kr.co.swm.adminPage.accommodation.model.dto.RoomForm;
import kr.co.swm.adminPage.accommodation.model.service.AccommodationServiceImpl;
import kr.co.swm.adminPage.accommodation.util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AccommodationController {

    private final UploadFile uploadFile;
    private final AccommodationServiceImpl accommodationService;

    @Autowired
    public AccommodationController(UploadFile uploadFile, AccommodationServiceImpl accommodationService) {
        this.uploadFile = uploadFile;
        this.accommodationService = accommodationService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/enroll")
    public String enroll(Model model) {
        System.out.println("in");

        model.addAttribute("location", new AccommodationDto());
        return "accommodation/enroll";
    }


    @PostMapping("/save-location")
    public ResponseEntity<?> saveLocation(@RequestParam("previewFiles") List<MultipartFile> subFile,
                                          @RequestParam("mainPhoto") List<MultipartFile> mainFile,
                                          @RequestParam MultiValueMap<String, String> rooms,
                                          @ModelAttribute AccommodationDto accommodationDto,
                                          @ModelAttribute RoomForm roomForm
    ) {


        System.out.println(mainFile.get(1).getOriginalFilename());
        AccommodationImageDto mainImage = uploadFile.uploadSingleFile(mainFile.get(1), "MAIN");
        System.out.println("main : " + mainImage);

        int result = accommodationService.saveAccommodation(accommodationDto, mainImage);

        System.out.println("result : " + result);


        for (int i = 1; i < roomForm.getRooms().size()  ; i++) {
            System.out.println("방 number : "  + roomForm.getRooms().get(i));
        }

        System.out.println(accommodationDto.getAccommodationName());

        System.out.println("info : " + accommodationDto.getAccommodationInfo());
        System.out.println("selected : " + accommodationDto.getAccommodationType());
        System.out.println("ac : " + accommodationDto.getRoomName());

        System.out.println("name1 " + rooms.get("roomName"));
        System.out.println("cccc " + rooms.get("standardOccupation"));
        System.out.println(mainFile.size() + " main");


        System.out.println("phoen : " + accommodationDto.getAccommodationPhone() );



        System.out.println();

        int roomsSize = roomForm.getRooms().size()-1;
        int startIndex =  roomsSize;

//        System.out.println(roomForm.getRooms().get(i).getRoomName());
        for (int i = 1; i < roomForm.getRooms().size(); i++) {
            System.out.println("222222 : " + roomForm.getRooms().get(i).getRoomCount());
            System.out.println("====>  " + roomForm.getRooms().get(i).getRoomCategory());
            String roomCategory = roomForm.getRooms().get(i).getRoomCategory();
            System.out.println("cc : " + roomForm.getRooms().get(i).getRoomName());
            String checkIn = roomForm.getRooms().get(i).getCheckInTime();
            String checkOut = roomForm.getRooms().get(i).getCheckOutTime();

            System.out.println("checkIn : " + roomForm.getRooms().get(i).getCheckInTime());
            System.out.println("chckkk : " + accommodationDto.getCheckInTime());

            String roomName = roomForm.getRooms().get(i).getRoomName();

            // 객실1에 이미지 1개, 객실2에 이미지 2개
            // endIndex = 1, 3
            // roomsSize = 2
            // startIndex = 2;
            // 배열 접근할 때 객실1은 2번, 객실2는 3~4
            for (int j = 0; j < accommodationDto.getRoomValues(); j++) {
                int enrollRoom = accommodationService.enrollRooms(accommodationDto, roomCategory, roomName, checkIn,checkOut);
            }
            for (int k = startIndex; k <= roomForm.getRooms().get(i).getEndIndex()+roomsSize-1; k++) {

                System.out.println(" K : " + k);
                System.out.println("subFile Length : " + subFile.size());
                System.out.println(subFile.get(k).getOriginalFilename());
                AccommodationImageDto roomsImage = uploadFile.uploadSingleFile(subFile.get(k), "PREVIEW");
//                accommodationService.enrollSubImages(roomsImage);
                System.out.println();
            }
            startIndex = roomForm.getRooms().get(i).getEndIndex()+ roomsSize;
        }
        System.out.println("==========");


        Map<String, String> response = new HashMap<>();
        try {
//            List<AccommodationImageDto> images = uploadFile.upload(subFile);


//            int result = accommodationService.saveAccommodation(rooms);
//            System.out.println( "result = " + result);

            response.put("message", "Upload successful!");
            // 데이터 저장 로직 수행
            response.put("success", "true");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();

            response.put("success", "false");
            response.put("message", "Error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

//
//@PostMapping("/boardEnroll")
//@ResponseBody
//public ResponseEntity<Map<String, String>> enrollUsedBoard(@RequestParam("mainFile") MultipartFile mainFile,
//                                                           @RequestParam("previewFiles") List<MultipartFile> previewFiles,
//                                                           @ModelAttribute("usedBoard") UsedBoardDto usedBoard,
//                                                           Model model){
//    Map<String, String> response = new HashMap<>();
//    try {
//        model.addAttribute("kakaoMap", kakaoMap);
//
//        List<MultipartFile> mainImages = List.of(mainFile);
//        List<UsedBoardImageDto> images = uploadFile.upload(mainImages, previewFiles);
//        usedBoard.setImages(images);
//
//        int result = usedBoardService.enrollUsedBoard(usedBoard);
//
//        response.put("message", "Upload successful!");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    } catch (Exception e) {
//        e.printStackTrace();
//        response.put("message", "Upload failed!");
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
