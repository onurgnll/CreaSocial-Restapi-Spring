package com.app.facebookclone.services;

import com.app.facebookclone.entities.User;
import com.app.facebookclone.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username);
    }


    public User findUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("sad"));
    }


    public User findByEmail(String email){
        return userRepo.findByEmail(email);
    }

    public User save(User user) {
        return userRepo.save(user);
    }



}
