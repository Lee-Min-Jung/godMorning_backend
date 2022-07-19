package com.godMorning_backend.service;

import com.godMorning_backend.domain.Heart;
import com.godMorning_backend.repository.JDBCHeartRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HeartServiceImpl {

    private final JDBCHeartRepository jdbcHeartRepository;

    public HeartServiceImpl(JDBCHeartRepository jdbcHeartRepository) {
        this.jdbcHeartRepository = jdbcHeartRepository;
    }

    public String deleteHeart(Long id, Long post_no) {
    return jdbcHeartRepository.deleteHeart(id,post_no);
    }

    public String insertHeart(Heart heart) {
        if (jdbcHeartRepository.findByUserIdAndPost_no(heart.getId(), heart.getPost_no()).isPresent()) {
            return "~";
        } else {
            jdbcHeartRepository.insertHeart(heart);
        }

        return String.valueOf(heart.getId());
    }

/*
    public void heartIncrement(Heart heart) {
       if (heart.getH_number() == 0) {
           jdbcHeartRepository.heartIncrement(heart);
       }
       else
        jdbcHeartRepository.heartIncrement(heart);
    }
*/
    public List<Heart> heartRank() {

        return jdbcHeartRepository.heartRank();
    }
}
