package com.godMorning_backend.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter

@Configuration
public class Scrap {

    private long post_no;//스크랩한 게시글 번호
    private long id;//스크랩한 회원 아이디


}
