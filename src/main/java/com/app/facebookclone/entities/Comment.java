package com.app.facebookclone.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    private String content;

    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Like> likes;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Media> commentMedias;


}
