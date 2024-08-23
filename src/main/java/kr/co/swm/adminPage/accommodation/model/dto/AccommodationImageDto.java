package kr.co.swm.adminPage.accommodation.model.dto;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationImageDto {

    private String uploadOriginName;
    private String uploadUniqueName;
    private String uploadImagePath;
    private String uploadImageType;

}
