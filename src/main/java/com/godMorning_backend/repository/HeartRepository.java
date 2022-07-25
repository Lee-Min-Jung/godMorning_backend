package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Heart;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HeartRepository {

    String deleteHeart(Long id, Long post_no);

    void insertHeart(Heart heart);

    List<Heart> heartRank();
    Optional<Heart> findByUserIdAndPost_no(Long id, Long post_no);


}
