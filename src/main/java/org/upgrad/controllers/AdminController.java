package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        }
        else if (user.getRole().equalsIgnoreCase("admin")) {
            userService.deleteUser(userId);
            userProfileService.deleteUser(userId);
            return new ResponseEntity<>("User with userId " + userId + " deleted successfully!", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("You do not have rights to delete a user!", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/users/all")
    public ResponseEntity<?> getAllUsers(HttpSession session) {

        User user = (User) session.getAttribute("currUser");
        if (user == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        else if (user.getRole().equalsIgnoreCase("admin")) {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("You do not have rights to access all users!", HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/category")
    public ResponseEntity<String> categoriesCreation(@RequestParam String categoryTitle, @RequestParam String description, HttpSession session ){

        User user = (User) session.getAttribute("currUser");
        if(user==null) return new ResponseEntity<>("Please Login first to access this endpoint!",HttpStatus.UNAUTHORIZED);
        else if (user.getRole().equalsIgnoreCase("admin")) {
            categoryService.savecategory(categoryTitle, description);
            return new ResponseEntity<>(categoryTitle + " category added successfully.",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("You do not have rights to add categories.",HttpStatus.UNAUTHORIZED);
        }
    }
}