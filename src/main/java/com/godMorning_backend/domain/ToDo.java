package com.godMorning_backend.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ToDo {

    private int post_no;
    private String content;
    private int check_do;

    List<ToDo> todo_list;
}
