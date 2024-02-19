package com.app.facebookclone.services;

import com.app.facebookclone.entities.PersonalStatements;
import com.app.facebookclone.entities.User;
import com.app.facebookclone.exceptions.NotFoundException;
import com.app.facebookclone.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class UserService implements UserDetailsService {

    private UserRepo userRepo;

    private PersonalStatementsService personalStatementsService;

    public UserService(UserRepo userRepo, PersonalStatementsService personalStatementsService) {
        this.userRepo = userRepo;
        this.personalStatementsService = personalStatementsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username);
    }


    public User findUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("Not found user"));
    }


    public User findByEmail(String email){
        return userRepo.findByEmail(email);
    }

    public User save(User user) {
        return userRepo.save(user);
    }


    public HashMap<String , Object> getUserDetails(Long userId) {

        User user = findUserById(userId);

        HashMap<String , Object> hashMap = new HashMap<>();

        hashMap.put("user" , user);
        hashMap.put("details" , user.getPersonalStatements());

        return hashMap;
    }
}
