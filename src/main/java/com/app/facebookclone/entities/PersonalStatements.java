package com.app.facebookclone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "personal_statements")
public class PersonalStatements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_statements_id")
    private Long personalStatementsId;

    private String biography;

    private Date birthday;

    private String profileImage;

    private String coverImage;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "personalStatements")
    private List<Education> educations;

}
