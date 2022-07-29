package com.godMorning_backend.domain;

import com.nimbusds.oauth2.sdk.util.date.SimpleDate;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.List;

@Getter
@Setter
public class Routine {

    private Long post_no; //게시글 번호
    private Long id;//작성자 번호
    private String nickname;//작성자 닉네임
    private String title;//게시글 제목
    private String create_date;//작성일
    private String startTime;//시작시간
    private String endTime;//끝내는시간
    private int heartCount;//하트수
    private int scrapCount;//스크랩수
    List<ToDo> todo_list;//투두 목록



}
