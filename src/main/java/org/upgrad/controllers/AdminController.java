package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upgrad.models.User;
import org.upgrad.services.CategoryService;
import org.upgrad.services.UserProfileService;
import org.upgrad.services.UserService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;


    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer userId, HttpSession session) {

        User user = (User) session.getAttribute("currUser");
        if (user == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if (user.getRole().equalsIgnoreCase("admin")) {
            userService.deleteUser(userId);
            userProfileService.deleteUser(userId);
            return new ResponseEntity<>("User with userId " + userId + " deleted successfully!", HttpStatus.OK);
        } else return new ResponseEntity<>("You do not have rights to delete a user!", HttpStatus.UNAUTHORIZED);
    }
}