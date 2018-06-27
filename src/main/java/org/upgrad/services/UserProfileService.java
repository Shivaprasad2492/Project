package org.upgrad.services;

import org.upgrad.models.UserProfile;



import java.util.Date;

public interface UserProfileService {

    void deleteUser(Integer userId);

    UserProfile getUser(Integer userId);

    void saveUser(Integer userId,String firstName,String lastName, String aboutMe, Date dob, String contactNumber, String country);
}
