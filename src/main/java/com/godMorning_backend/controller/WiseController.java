package com.godMorning_backend.controller;

import com.godMorning_backend.service.WiseServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WiseController {
    private WiseServiceImpl wiseServiceImpl;

    public WiseController(WiseServiceImpl wiseServiceImpl){
        this.wiseServiceImpl = wiseServiceImpl;
    }

    @RequestMapping(value="wiseSaying")
    public String randomWiseSaying(){
        return wiseServiceImpl.findByNo();
    }
}
