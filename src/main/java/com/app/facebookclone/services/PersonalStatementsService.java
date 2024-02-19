package com.app.facebookclone.services;

import com.app.facebookclone.entities.Education;
import com.app.facebookclone.entities.PersonalStatements;
import com.app.facebookclone.repos.EducationRepo;
import com.app.facebookclone.repos.PersonalStatementsRepo;
import com.app.facebookclone.requests.AddEducation;
import com.app.facebookclone.requests.EditDetailsRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonalStatementsService {

    private PersonalStatementsRepo personalStatementsRepo;
    private EducationRepo educationRepo;

    public PersonalStatementsService(PersonalStatementsRepo personalStatementsRepo, EducationRepo educationRepo) {
        this.personalStatementsRepo = personalStatementsRepo;
        this.educationRepo = educationRepo;
    }



    public PersonalStatements findByUserId(Long userId) {
        return personalStatementsRepo.findByUserUserId(userId);
    }

    public PersonalStatements save(PersonalStatements personalStatements) {
        return personalStatementsRepo.save(personalStatements);
    }

    public PersonalStatements editUserDetails(Long userId, EditDetailsRequest editDetailsRequest) {

        PersonalStatements personalStatements = findByUserId(userId);

        if(editDetailsRequest.getBirthday() != null){
            personalStatements.setBirthday(editDetailsRequest.getBirthday());
        }
        if(editDetailsRequest.getBiography() != null){
            personalStatements.setBiography(editDetailsRequest.getBiography());
        }


        return save(personalStatements);
    }

    public PersonalStatements addEducation(Long userId, AddEducation addEducation) {

        PersonalStatements personalStatements = findByUserId(userId);

        Education education = new Education();

        education.setDescription(addEducation.getDescription());

        education.setEndDate(addEducation.getEndDate());
        education.setStartDate(addEducation.getStartDate());
        education.setIsGraduated(addEducation.getIsGraduated());
        education.setSchoolName(addEducation.getSchoolName());
        education.setPersonalStatements(personalStatements);

        educationRepo.save(education);

        personalStatements.getEducations().add(education);


        return personalStatements;
    }
}
