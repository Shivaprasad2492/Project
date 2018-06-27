package org.upgrad.controllers;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.User;
import org.upgrad.services.UserProfileService;
import org.upgrad.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/signup")
    public ResponseEntity<String> PostRegistered(@RequestParam String firstName, @RequestParam(required = false) String lastName, @RequestParam(name = "userName") String userName, @RequestParam String email, @RequestParam String password, @RequestParam String country, @RequestParam(required = false) String aboutMe, @RequestParam(defaultValue = "2001-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob, @RequestParam(required = false) String contactNumber) {
        if (userService.findUserByUsername(userName) != null) {
            return new ResponseEntity<>("Try any other Username, this Username has already been taken.", HttpStatus.FORBIDDEN);
        } else if (userService.findUserByEmail(email) != null) {
            return new ResponseEntity<>("This user has already been registered, try with any other emailId.", HttpStatus.FORBIDDEN);
        } else {
            String sha256hex = Hashing.sha256()
                    .hashString(password, Charsets.US_ASCII)
                    .toString();
            userService.saveUser(userName, email, sha256hex);
            Integer user_id = userService.findUserId(userName);
            userProfileService.saveUser(user_id, firstName, lastName, aboutMe, dob, contactNumber, country);
            return new ResponseEntity<>(userName + " successfully registered", HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> signin(@RequestParam String userName, @RequestParam String password,HttpSession session){
        String passwordByUser = String.valueOf(userService.findUserPassword(userName));
        String roleByUser = String.valueOf(userService.findUserRole(userName));
        String sha256hex = Hashing.sha256()
                .hashString(password, Charsets.US_ASCII)
                .toString();
        if (!(passwordByUser.equalsIgnoreCase(sha256hex))) {
            return new ResponseEntity<>("Invalid Credentials",HttpStatus.UNAUTHORIZED);
        }
        else if((roleByUser.equalsIgnoreCase("admin"))){
            User user = new User(userName,roleByUser);
            session.setAttribute("currUser",user);
            return new ResponseEntity<>("You have logged in as admin!",HttpStatus.OK);
        }
        else{
            User user = new User(userName,roleByUser);
            session.setAttribute("currUser",user);
            return new ResponseEntity<>("You have logged in successfully!",HttpStatus.OK);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> signout(HttpSession session){
        if(session.getAttribute("currUser")==null) return new ResponseEntity<>("You are currently not logged in",HttpStatus.UNAUTHORIZED);
        else{
            session.removeAttribute("currUser");
            return new ResponseEntity<>("You have logged out successfully!",HttpStatus.OK);}
    }

    @GetMapping("/userprofile/{userId}")
    public ResponseEntity<?> userProfile(@PathVariable("userId") Integer userId,HttpSession session){
        if(session.getAttribute("currUser")!=null) {
            if(userProfileService.getUser(userId)==null) {
                return new ResponseEntity<>("User Profile not found!", HttpStatus.NOT_FOUND);
            }
            else{
                return new ResponseEntity<>(userProfileService.getUser(userId),HttpStatus.OK);
            }
        }
        else return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
    }
}
