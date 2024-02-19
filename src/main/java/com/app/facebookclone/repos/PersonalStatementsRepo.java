package com.app.facebookclone.repos;

import com.app.facebookclone.entities.PersonalStatements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalStatementsRepo extends JpaRepository<PersonalStatements , Long> {
    PersonalStatements findByUserUserId(Long userId);
}
