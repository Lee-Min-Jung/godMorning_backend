package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.Scrap;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository {
    void saveScrap(Scrap scrap);

    //스크랩 아이디로 조회
    List<Routine> findById(Long id);

    //
    Optional<Scrap> findByUserIdAndPost_no(Long id, Long post_no);

    //스크랩 삭제
    String deleteScrap(Long id, Long post_no);
}
