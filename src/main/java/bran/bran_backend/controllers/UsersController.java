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

import bran.bran_backend.models.Users;
import bran.bran_backend.services.UserService;
import org.springframework.web.bind.annotation.RequestBody;



/**
 *
 * @author bransmartuk
 */
@RestController
@RequestMapping("/api/user")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    private final UserService userService;
    public UsersController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Users>> GetUsers(){
        try {
            logger.info("Getting all users");
            List<Users> users = this.userService.GetUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error getting all users", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
        
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Users>> GetPendingUsers(){
        try {
            logger.info("Getting all pending users");
            List<Users> usersList = this.userService.GetPendingUsers();
            return ResponseEntity.ok(usersList);
        } catch (Exception e) {
            logger.error("Error getting all pending users", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Users> GetUserById(@PathVariable Long userId){
        logger.info("Getting user by id");
        Users user = this.userService.GetUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("update")
    public ResponseEntity<Void> UpdateUser(Users newUser){
        logger.info("Updating user");
        try {
            this.userService.UpdateUser(newUser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error updating user", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("{userId}/activate")
    public ResponseEntity<Void> ActivateUser(@PathVariable Long userId){
        logger.info("Activating user");
        this.userService.ActivateUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{userId}/deactivate")
    public ResponseEntity<Void> DeactivateUser(@PathVariable Long userId){
        logger.info("Deactivating user");
        this.userService.DeactivateUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{userId}/reject")
    public ResponseEntity<Void> RejectUser(@PathVariable Long userId){
        logger.info("Rejecting user");
        this.userService.RejectUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("email")
    public ResponseEntity<Void> PostEmailMember(String email){
        // Must have roles and email only role is the one this would create
        logger.info("Adding email to mailing list");
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping
    public ResponseEntity<Users> CreateUser(@RequestBody Users user) {
        logger.info("Creating new user");
        Users createdUser = userService.CreateUser(user);
        return ResponseEntity.ok(createdUser);
    }
    

}
