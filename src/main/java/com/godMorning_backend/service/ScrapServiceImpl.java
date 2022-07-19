package com.godMorning_backend.service;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.Scrap;
import com.godMorning_backend.repository.JDBCScrapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapServiceImpl implements ScrapService{
    private final JDBCScrapRepository jdbcScrapRepository;

    public ScrapServiceImpl(JDBCScrapRepository jdbcScrapRepository){
        this.jdbcScrapRepository = jdbcScrapRepository;
    }

    public String saveScrap(Scrap scrap) {
        if (jdbcScrapRepository.findByUserIdAndPost_no(scrap.getPost_no(),scrap.getId()).isPresent()){
            return "이미 스크랩되었습니다";
        } else {
            jdbcScrapRepository.saveScrap(scrap);
        }
        return String.valueOf(scrap.getId());
    }

    public List<Routine> findById(Long id) {
        return jdbcScrapRepository.findById(id);
    }



    @Override
    public Routine findByIdAndPostNo(Long id, Long post_no) {
        return jdbcScrapRepository.findByIdAndPost_no(id, post_no);
    }

    public String deleteScrap(Long id, Long post_no){
        return jdbcScrapRepository.deleteScrap(id, post_no);
    }
}
