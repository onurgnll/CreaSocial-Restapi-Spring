package com.app.facebookclone.requests;

import lombok.Data;

import java.util.Date;

@Data
public class EditDetailsRequest{
    private Date birthday;
    private String biography;

}
