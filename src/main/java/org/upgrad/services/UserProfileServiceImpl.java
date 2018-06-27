package org.upgrad.services;


import org.springframework.stereotype.Service;
import org.upgrad.models.UserProfile;
import org.upgrad.repositories.UserProfileRepository;

import java.util.Date;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public void deleteUser(Integer userId){ userProfileRepository.deleteUser(userId);}

    @Override
    public void saveUser(Integer userId,String firstName,String lastName, String aboutMe, Date dob, String contactNumber, String country) {
        userProfileRepository.save(userId,firstName,lastName,aboutMe,dob,contactNumber,country);
    }

    @Override
    public UserProfile getUser(Integer userId){ return userProfileRepository.getUser(userId);}
}
