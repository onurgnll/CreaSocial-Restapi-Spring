package com.app.facebookclone.requests;

import lombok.Data;

import java.util.Date;

@Data
public class AddEducation {

    private String schoolName;
    private String description;
    private Date startDate;
    private Date endDate;

    private Boolean isGraduated;

}
