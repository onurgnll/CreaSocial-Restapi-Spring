package com.app.facebookclone.controllers;

import com.app.facebookclone.requests.LoginRequest;
import com.app.facebookclone.requests.RegisterRequest;
import com.app.facebookclone.requests.VerifyAccountReq;
import com.app.facebookclone.response.ResponseHandler;
import com.app.facebookclone.services.AuthService;
import com.app.facebookclone.services.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterRequest registerRequest){
        return ResponseHandler.generateResponse(200, authService.registerUser(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> registerUser(@RequestBody LoginRequest loginRequest){
        return ResponseHandler.generateResponse(200, authService.login(loginRequest));
    }

    @PostMapping("/verifyaccount/{userId}")
    public ResponseEntity<Object> verifyAccount(@RequestBody VerifyAccountReq verifyAccountReq , @PathVariable Long userId){
        return ResponseHandler.generateResponse(200, authService.verifyAccount(verifyAccountReq ,userId));
    }

}
