package com.godMorning_backend.controller;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.Scrap;
import com.godMorning_backend.repository.JDBCScrapRepository;
import com.godMorning_backend.service.ScrapServiceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class ScrapController {
    private final JdbcTemplate jdbcTemplate;
    private ScrapServiceImpl scrapServiceImpl;
    private Scrap scrap;
    private JDBCScrapRepository jdbcScrapRepository;
    private List<Scrap> scrapSet;

    public ScrapController(JdbcTemplate jdbcTemplate, ScrapServiceImpl scrapServiceImpl, Scrap scrap, JDBCScrapRepository jdbcScrapRepository, List<Scrap> scrapSet) {
        this.jdbcTemplate = jdbcTemplate;
        this.scrapServiceImpl = scrapServiceImpl;
        this.jdbcScrapRepository = jdbcScrapRepository;
        this.scrap = scrap;
    }

    @ResponseBody
    @PostMapping(value = "scrap/create", produces = "application/json; charset=UTF-8")
    public Scrap save(@RequestBody Scrap scr) {

        scr.setPost_no(scr.getPost_no());
        scr.setId(scr.getId());

        scrapServiceImpl.saveScrap(scr);
        return scr;
    }
    //아이디별 스크랩 전체 조회
    @RequestMapping(value="scrap/list/{id}")
    public List<Routine> timezoneList(@PathVariable("id") long id){
        return scrapServiceImpl.findById(id);
    }

    //아이디별 스크랩 조회 상세 보기
    @RequestMapping(value="scrap/list/{id}/{post_no}")
    public Routine timezoneDetail(@PathVariable("id") long id, @PathVariable("post_no") long post_no){
        return scrapServiceImpl.findByIdAndPostNo(id, post_no);
    }
    //아이디별 스크랩 전체 보기
//    @RequestMapping(value = "scrap/list", produces = "application/json; charset=UTF-8")
//    public List<Routine> findById(HttpServletRequest request){
//        Long id =  Long.parseLong(request.getParameter("id"));
//        return scrapServiceImpl.findById(id);
//    }

    //아이디별 스크랩 상세 보기
//    @RequestMapping(value = "scrap/list", produces = "application/json; charset=UTF-8")
//    public List<Routine> findById(HttpServletRequest request){
//        Long id =  Long.parseLong(request.getParameter("id"));
//        return scrapServiceImpl.findById(id);
//    }

    @RequestMapping(value = "scrap/delete")
    public String deleteScrap(HttpServletRequest request, Model model){
        Long id =  Long.parseLong(request.getParameter("id"));
        Long post_no = Long.parseLong(request.getParameter("post_no"));
        model.addAttribute("id", id);
        model.addAttribute("post_no", post_no);
        return scrapServiceImpl.deleteScrap(id, post_no);
    }
}
