package kr.co.swm.model.service;

import jakarta.servlet.http.HttpSession;
import kr.co.swm.model.dto.SellerDto;

import java.util.List;

public interface SellerService {

    public List<SellerDto> basicRate(String roomCode);

//    public int basicRateInsert(SellerDto sellerDto, HttpSession session);
    public int basicRateInsert(SellerDto sellerDto, String adminCode);


}
