package com.app.facebookclone.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "friendship")
@Data
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_id")
    private Long friendshipId;


    @ManyToOne(cascade =  {CascadeType.ALL, CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade =  {CascadeType.ALL, CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST})
    @JoinColumn(name = "friend_id")
    private User friend;

    private LocalDateTime createdAt;

}
