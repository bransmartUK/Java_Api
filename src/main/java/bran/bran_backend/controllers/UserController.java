/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package bran.bran_backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bran.bran_backend.models.User;
import bran.bran_backend.services.UserService;
import org.springframework.web.bind.annotation.RequestBody;



/**
 *
 * @author bransmartuk
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        try {
            logger.info("Getting all users");
            List<User> user = this.userService.getUsers();
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error getting all users", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
        
    }

    @GetMapping("/pending")
    public ResponseEntity<List<User>> getPendingUsers(){
        try {
            logger.info("Getting all pending users");
            List<User> usersList = this.userService.getPendingUsers();
            return ResponseEntity.ok(usersList);
        } catch (Exception e) {
            logger.error("Error getting all pending users", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId){
        logger.info("Getting user by id");
        User user = this.userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("update")
    public ResponseEntity<Void> updateUser(User newUser){
        logger.info("Updating user");
        try {
            this.userService.updateUser(newUser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error updating user", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("{userId}/activate")
    public ResponseEntity<Void> activateUser(@PathVariable Long userId){
        logger.info("Activating user");
        this.userService.activateUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{userId}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long userId){
        logger.info("Deactivating user");
        this.userService.deactivateUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{userId}/reject")
    public ResponseEntity<Void> rejectUser(@PathVariable Long userId){
        logger.info("Rejecting user");
        this.userService.rejectUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("email")
    public ResponseEntity<Void> postEmailMember(String email){
        //TODO: Must have roles and email only role is the one this would create
        logger.info("Adding email to mailing list");
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Creating new user");
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
    

}
