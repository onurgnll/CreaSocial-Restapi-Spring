package com.app.facebookclone.repos;

import com.app.facebookclone.entities.FriendshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRequestRepo extends JpaRepository<FriendshipRequest , Long> {
}
