package com.app.facebookclone.repos;

import com.app.facebookclone.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepo extends JpaRepository<Like, Long> {
}
