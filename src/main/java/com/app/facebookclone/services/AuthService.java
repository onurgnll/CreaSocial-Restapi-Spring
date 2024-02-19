package com.app.facebookclone.services;

import com.app.facebookclone.entities.PersonalStatements;
import com.app.facebookclone.entities.Role;
import com.app.facebookclone.entities.User;
import com.app.facebookclone.exceptions.AlreadyExistException;
import com.app.facebookclone.exceptions.NotFoundException;
import com.app.facebookclone.exceptions.WrongInputException;
import com.app.facebookclone.repos.RoleRepo;
import com.app.facebookclone.requests.LoginRequest;
import com.app.facebookclone.requests.RegisterRequest;
import com.app.facebookclone.requests.VerifyAccountReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@Service
public class AuthService {

    private UserService userService;

    private JwtService jwtService;

    private RoleRepo roleRepo;

    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

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


    private String generateVerificationCode(){
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(characters.length());
            randomString.append(characters.charAt(randomIndex));
        }

        return randomString.toString();
    }

    public HashMap<String, Object> registerUser(RegisterRequest registerRequest) {

        HashMap<String, Object> hashMap = new HashMap<>();

        if (userService.loadUserByUsername(registerRequest.getEmail()) != null) {
            throw new AlreadyExistException("This Email Already Used");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User();

        user.setVerificationCode(generateVerificationCode());


        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setSurname(registerRequest.getSurname());
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        user.setAuthorities(new ArrayList<>());

        user.getAuthorities().add(roleRepo.findByName("ROLE_USER"));



        User savedUser = userService.save(user);

        emailService.sendSimpleMessage(registerRequest.getEmail(),"Kayıt Olundu" , "Email Doğrulama Kodun: " + user.getVerificationCode());


        hashMap.put("user", savedUser);

        PersonalStatements personalStatements = new PersonalStatements();

        personalStatements.setUser(savedUser);
        personalStatements.setCoverImage("http://localhost:3000/api/file/cover/default.png");
        personalStatements.setProfileImage("http://localhost:3000/api/file/profile/default.png");

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


    public User verifyAccount(VerifyAccountReq verifyAccountReq, Long userId) {


        User user = userService.findUserById(userId);

        if(user == null){
            throw  new NotFoundException("Not found user");
        }


        if(user.getVerificationCode().equals(verifyAccountReq.getVerificationCode())){
            Role role = roleRepo.findByName("ROLE_VERIFIED");

            user.getAuthorities().add(role);

            return userService.save(user);

        }else{
            throw new WrongInputException("Yanlış Kod");
        }


    }
}
