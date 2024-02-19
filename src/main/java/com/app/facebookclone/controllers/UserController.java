package com.app.facebookclone.controllers;

import com.app.facebookclone.requests.AddEducation;
import com.app.facebookclone.requests.EditDetailsRequest;
import com.app.facebookclone.response.ResponseHandler;
import com.app.facebookclone.services.PersonalStatementsService;
import com.app.facebookclone.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private PersonalStatementsService personalStatementsService;

    public UserController(UserService userService, PersonalStatementsService personalStatementsService) {
        this.userService = userService;
        this.personalStatementsService = personalStatementsService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserDetails(@PathVariable Long userId) {

        return ResponseHandler.generateResponse(200, userService.getUserDetails(userId));

    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> editUserDetails(@PathVariable Long userId, @RequestBody EditDetailsRequest editDetailsRequest) {

        return ResponseHandler.generateResponse(200, personalStatementsService.editUserDetails(userId, editDetailsRequest));

    }
    @PutMapping("/{userId}/education")
    public ResponseEntity<Object> addEducation(@PathVariable Long userId, @RequestBody AddEducation addEducation) {

        return ResponseHandler.generateResponse(200, personalStatementsService.addEducation(userId, addEducation));

    }


}
