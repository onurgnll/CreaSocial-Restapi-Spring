package com.app.facebookclone.services;

import com.app.facebookclone.entities.PersonalStatements;
import com.app.facebookclone.entities.User;
import com.app.facebookclone.exceptions.AlreadyExistException;
import com.app.facebookclone.exceptions.NotFoundException;
import com.app.facebookclone.exceptions.WrongInputException;
import com.app.facebookclone.repos.RoleRepo;
import com.app.facebookclone.requests.LoginRequest;
import com.app.facebookclone.requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class AuthService {

    private UserService userService;

    private JwtService jwtService;

    private RoleRepo roleRepo;

    private AuthenticationManager authenticationManager;
    private PersonalStatementsService personalStatementsService;

    @Autowired
    public void setPersonalStatementsService(PersonalStatementsService personalStatementsService) {
        this.personalStatementsService = personalStatementsService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    public void setRoleRepo(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }



    public HashMap<String, Object> registerUser(RegisterRequest registerRequest) {

        HashMap<String, Object> hashMap = new HashMap<>();

        if (userService.loadUserByUsername(registerRequest.getEmail()) != null) {
            throw new AlreadyExistException("This Email Already Used");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setSurname(registerRequest.getSurname());
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        user.setAuthorities(new ArrayList<>());

        user.getAuthorities().add(roleRepo.findByName("ROLE_USER"));



        User savedUser = userService.save(user);

        hashMap.put("user", savedUser);

        PersonalStatements personalStatements = new PersonalStatements();

        personalStatements.setUser(savedUser);

        personalStatementsService.save(personalStatements);



        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword()));

        if (authentication.isAuthenticated()) {

            hashMap.put("token", jwtService.generateToken(user.getEmail()));
            return hashMap;

        }
        else{
            throw  new RuntimeException();
        }


    }



    public HashMap<String, Object> login(LoginRequest loginRequest){


        User user = userService.findByEmail(loginRequest.getEmail());

        if(user == null){
            throw  new NotFoundException("Not found user");
        }

        HashMap<String , Object> hashMap = new HashMap<String , Object> ();

        hashMap.put("user", user);


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            hashMap.put("token" , jwtService.generateToken(loginRequest.getEmail()));
            return hashMap;
        }
        throw new WrongInputException("Wrong Password   ");
    }




}
