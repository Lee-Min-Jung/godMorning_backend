package com.godMorning_backend.service;

import com.godMorning_backend.domain.Heart;
import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.dto.HeartRank;
import com.godMorning_backend.repository.JDBCHeartRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HeartServiceImpl implements HeartService {

    private final JDBCHeartRepository jdbcHeartRepository;

    public HeartServiceImpl(JDBCHeartRepository jdbcHeartRepository) {
        this.jdbcHeartRepository = jdbcHeartRepository;
    }

    //하트 삭제
    public String deleteHeart(Long id, Long post_no) {
    return jdbcHeartRepository.deleteHeart(id,post_no);
    }

    //하트 실행
    public String insertHeart(Heart heart) {
        if (jdbcHeartRepository.findByUserIdAndPost_no(heart.getId(), heart.getPost_no()).isPresent()) {
            return "~";
        } else {
            jdbcHeartRepository.insertHeart(heart);
        }

        return String.valueOf(heart.getId());
    }


    //하트 많은 순서대로 보기
    public List<Routine> heartRank() {

    return jdbcHeartRepository.heartRank();
    }

    //하트 많은 순서대로 상세보기
    public Routine heartRankDetail(Long post_no){
        return jdbcHeartRepository.heartRankDetail(post_no);
    }
}
