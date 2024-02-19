package com.app.facebookclone.controllers;

import com.app.facebookclone.requests.AddFriendRequest;
import com.app.facebookclone.requests.FriendshipRequestResponseRequest;
import com.app.facebookclone.response.ResponseHandler;
import com.app.facebookclone.services.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friends")
public class FriendController {

    private FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserFriends(@PathVariable Long userId , @RequestParam(name = "page" , defaultValue = "0") int page){

        return ResponseHandler.generateResponse(200, friendService.getUserFriends(userId,page) );
    }
    @GetMapping("/requests/{userId}")
    public ResponseEntity<Object> getUserFriendRequests(@PathVariable Long userId , @RequestParam(name = "page" , defaultValue = "0") int page){

        return ResponseHandler.generateResponse(200, friendService.getUserFriendRequests(userId , page) );
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Object> addFriend(@PathVariable Long userId,@RequestBody AddFriendRequest addFriendRequest){
        friendService.addFriend(userId,addFriendRequest);
        return ResponseHandler.generateResponse(200, "Request sended successfully" );
    }


    @PostMapping("/requests/{requestId}")
    public ResponseEntity<Object> responseFriendRequest(@PathVariable Long requestId,@RequestBody FriendshipRequestResponseRequest friendshipRequestResponseRequest) {
        friendService.responseFriendRequest(requestId,friendshipRequestResponseRequest);
        return ResponseHandler.generateResponse(200, "response sended successfully");
    }

}
