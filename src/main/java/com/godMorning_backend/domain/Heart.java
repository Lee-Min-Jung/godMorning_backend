package com.godMorning_backend.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter

@Configuration
public class Heart {

    private long h_number;
    // 게시글 번호
    private long post_no;
    // 회원 번호
    private long id;

}