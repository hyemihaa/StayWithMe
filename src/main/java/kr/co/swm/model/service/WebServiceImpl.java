package kr.co.swm.model.service;

import kr.co.swm.mappers.WebMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebServiceImpl implements WebService {

    private final WebMapper mapper;

    @Autowired
    public WebServiceImpl(WebMapper mapper) {
        this.mapper = mapper;
    }

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□




}
