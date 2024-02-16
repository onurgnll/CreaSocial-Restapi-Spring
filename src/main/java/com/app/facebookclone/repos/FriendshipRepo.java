package com.app.facebookclone.repos;

import com.app.facebookclone.entities.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepo extends JpaRepository<Friendship , Long> {
}
