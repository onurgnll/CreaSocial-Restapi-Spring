package com.app.facebookclone.repos;

import com.app.facebookclone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepo extends JpaRepository<User, Long> {
    UserDetails findByEmail(String username);
}
