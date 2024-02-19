package com.app.facebookclone.services;

import com.app.facebookclone.entities.Friendship;
import com.app.facebookclone.entities.FriendshipRequest;
import com.app.facebookclone.entities.User;
import com.app.facebookclone.exceptions.AlreadyExistException;
import com.app.facebookclone.exceptions.NotFoundException;
import com.app.facebookclone.repos.FriendshipRepo;
import com.app.facebookclone.repos.FriendshipRequestRepo;
import com.app.facebookclone.requests.AddFriendRequest;
import com.app.facebookclone.requests.FriendshipRequestResponseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class FriendService {


    private FriendshipRepo friendshipRepo;
    private FriendshipRequestRepo friendshipRequestRepo;
    private UserService userService;

    public FriendService(FriendshipRepo friendshipRepo, FriendshipRequestRepo friendshipRequestRepo, UserService userService) {
        this.friendshipRepo = friendshipRepo;
        this.friendshipRequestRepo = friendshipRequestRepo;
        this.userService = userService;
    }

    public Page<User> getUserFriends(Long userId , int page) {

        User user = userService.findUserById(userId);

        List<Friendship> userFriendships = user.getUserFriends();


        List<User> usersFriends = userFriendships.stream()
                .map(Friendship::getFriend) // Assuming Friendship has a method to get the friend
                .collect(Collectors.toList());


        Pageable pageable = PageRequest.of(page,10);


        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), usersFriends.size());

        if (start > usersFriends.size()) {
            return Page.empty(pageable);
        } else {
            return new PageImpl<>(usersFriends.subList(start, end), pageable, usersFriends.size());
        }


    }

    public void addFriend(Long userId, AddFriendRequest addFriendRequest) {

        User fromUser = userService.findUserById(userId);
        User toUser = userService.findUserById(addFriendRequest.getToUser());

        AtomicBoolean isFromUserRequestedBefore = new AtomicBoolean(false);


        toUser.getFriendRequests().forEach(e -> {
            if (Objects.equals(e.getSender().getUserId(), fromUser.getUserId())) {

                isFromUserRequestedBefore.set(true);
            }

        });

        if (!isFromUserRequestedBefore.get()) {

            FriendshipRequest friendshipRequest = new FriendshipRequest();

            friendshipRequest.setSender(fromUser);
            friendshipRequest.setReceiver(toUser);
            friendshipRequest.setCreatedAt(LocalDateTime.now());

            friendshipRequestRepo.save(friendshipRequest);
        } else {
            throw new AlreadyExistException("You have already requested user");
        }


    }

    public Page<HashMap> getUserFriendRequests(Long userId, int page) {

        Pageable pageable = PageRequest.of(page, 1);

        User user = userService.findUserById(userId);


        List<FriendshipRequest> friendshipRequestList = user.getFriendRequests();


        List<HashMap> hashMapList = new ArrayList<>();

        friendshipRequestList.forEach(e-> {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("user" , e.getSender());
            hashMap.put("request", e);
            hashMapList.add(hashMap);
        });




        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), hashMapList.size());

        if (start > hashMapList.size()) {
            return Page.empty(pageable);
        } else {
            return new PageImpl<>(hashMapList.subList(start, end), pageable, hashMapList.size());
        }

    }

    public void responseFriendRequest(Long requestId, FriendshipRequestResponseRequest friendshipRequestResponseRequest) {

        FriendshipRequest friendshipRequest = friendshipRequestRepo.findById(requestId).orElseThrow(()-> new NotFoundException("not found request"));

        if(friendshipRequestResponseRequest.isAccepted()){
            User acceptedUser = userService.findUserById(friendshipRequest.getReceiver().getUserId());
            User senderUser = userService.findUserById(friendshipRequest.getSender().getUserId());

            Friendship friendship = new Friendship();
            friendship.setUser(senderUser);
            friendship.setFriend(acceptedUser);
            friendship.setCreatedAt(LocalDateTime.now());

            friendshipRepo.save(friendship);

            Friendship friendship2 = new Friendship();
            friendship2.setUser(acceptedUser);
            friendship2.setFriend(senderUser);
            friendship2.setCreatedAt(LocalDateTime.now());

            friendshipRepo.save(friendship2);



        }
        friendshipRequestRepo.delete(friendshipRequest);

    }
}
