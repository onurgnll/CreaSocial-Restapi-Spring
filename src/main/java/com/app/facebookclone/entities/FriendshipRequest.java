package com.app.facebookclone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Table(name = "friendship_request")
@Entity
@Data
public class FriendshipRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_request_id")
    private Long friendshipRequestId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @JsonIgnore
    @Column(nullable = false)
    private boolean accepted;


    private LocalDateTime createdAt;

}
