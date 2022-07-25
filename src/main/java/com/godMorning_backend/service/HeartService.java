package com.godMorning_backend.service;

import com.godMorning_backend.domain.Heart;
import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.dto.HeartRank;

import java.util.List;
import java.util.Map;

public interface HeartService {


    String insertHeart(Heart heart);

    String deleteHeart(Long id, Long post_no);


    List<Routine> heartRank();

    Routine heartRankDetail(Long post_no);


    /*
    void heartIncrement(Heart heart);
    * */
}
