package kr.co.swm.adminPage.accommodation.util;


import kr.co.swm.adminPage.accommodation.model.dto.AccommodationImageDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class UploadFile {

    // UPLOAD_PATH에 이미지가 담길 경로를 써주세요

//    private final String UPLOAD_PATH = "C:\\dev\\work-space\\FinalProject\\swm_final\\src\\main\\resources\\static\\accommodationImages\\";
    private final String UPLOAD_PATH =  "C:\\dev\\work-space\\FinalProject\\src\\main\\resources\\static\\accommodationImages\\";
    public List<AccommodationImageDto> upload(List<MultipartFile> mainImage, List<MultipartFile> subImage) {
        System.out.println("uploadFile ========");
        List<AccommodationImageDto> mainImageDtos = mainImage.stream()
                .map(file -> uploadSingleFile(file, "MAIN"))
                .collect(Collectors.toList());

        List<AccommodationImageDto> previewImageDtos = subImage.stream()
                .map(file -> uploadSingleFile(file, "PREVIEW"))
                .collect(Collectors.toList());

        mainImageDtos.addAll(previewImageDtos)
        ;
        return mainImageDtos;
    }

    public AccommodationImageDto uploadSingleFile(MultipartFile upload, String imageType) {
        if (upload.isEmpty()) {
            return null;
        }

        String originName = upload.getOriginalFilename();
        String extension = originName.substring(originName.lastIndexOf('.'));
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        String output = now.format(formatter);

        int stringLength = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        String randomString = random.ints(stringLength, 0, characters.length())
                .mapToObj(characters::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());

        String fileName = output + "_" + randomString + extension;
        String filePathName = UPLOAD_PATH + fileName;
        Path filePath = Paths.get(filePathName);
        try {
            upload.transferTo(filePath);
            return new AccommodationImageDto(originName, fileName, UPLOAD_PATH, imageType);
        } catch (IllegalStateException | IOException e) {
            System.out.println("오류");
            throw new RuntimeException("이미지 업로드 도중 실패되었습니다.");
        }
    }
}
