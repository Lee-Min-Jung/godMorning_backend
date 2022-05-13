package com.godMorning_backend.domain;

import com.nimbusds.oauth2.sdk.util.date.SimpleDate;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;

@Getter
@Setter
public class Routine {


    private Long id;
    private String title;
    private String timezone;
    private String create_time;




}
