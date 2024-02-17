package com.app.facebookclone.services;

import com.app.facebookclone.entities.PersonalStatements;
import com.app.facebookclone.repos.PersonalStatementsRepo;
import org.springframework.stereotype.Service;

@Service
public class PersonalStatementsService {

    private PersonalStatementsRepo personalStatementsRepo;

    public PersonalStatementsService(PersonalStatementsRepo personalStatementsRepo) {
        this.personalStatementsRepo = personalStatementsRepo;
    }


    public PersonalStatements save(PersonalStatements personalStatements){
        return personalStatementsRepo.save(personalStatements);
    }
}
