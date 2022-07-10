package com.godMorning_backend.service;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.Scrap;

import java.util.List;

public interface ScrapService {
    String saveScrap(Scrap scrap);
    //아이디별 스크랩 조회
    List<Routine> findById(Long id);

    //스크랩 취소
    String deleteScrap(Long id, Long post_no);
}
