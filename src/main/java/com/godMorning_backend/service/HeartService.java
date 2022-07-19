package com.godMorning_backend.service;

import com.godMorning_backend.domain.Heart;

import java.util.List;
import java.util.Map;

public interface HeartService {
    Heart findHeart(long post_no, long id);

    String insertHeart(Heart heart);

    String deleteHeart(Long id, Long post_no);

    Heart findHeart(Map<String, Long> number);

    void heartIncrement(Heart heart);

    List<Heart> heartRank(Long post_no);
}
