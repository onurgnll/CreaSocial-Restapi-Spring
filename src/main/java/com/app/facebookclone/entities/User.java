package com.app.facebookclone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", unique = true)
    private String email;

    private String name;

    private String surname;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String verificationCode;


    @OneToOne(mappedBy = "user")
    private PersonalStatements personalStatements;


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Friendship> userFriends;

    @JsonIgnore
    @OneToMany(mappedBy = "receiver")
    private List<FriendshipRequest> friendRequests;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Post> posts;


    @JsonIgnore
    private boolean accountNonExpired = true;
    @JsonIgnore
    private boolean accountNonLocked = true;
    @JsonIgnore
    private boolean isEnabled = true;
    @JsonIgnore
    private boolean credentialsNonExpired = true;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role" , joinColumns = @JoinColumn(name = "user_id") , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> authorities;


    @Override
    public String getUsername() {
        return email;
    }
}
