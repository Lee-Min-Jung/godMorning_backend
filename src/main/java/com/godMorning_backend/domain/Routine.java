package com.godMorning_backend.domain;

import com.nimbusds.oauth2.sdk.util.date.SimpleDate;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.List;

@Getter
@Setter
public class Routine {

    private Long post_no;
    private Long id;
    private String username;
    private String title;
    private String create_date;
    private String startTime;
    private String endTime;
    List<ToDo> todo_list;



}
