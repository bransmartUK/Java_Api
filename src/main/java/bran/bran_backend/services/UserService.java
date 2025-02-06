/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package bran.bran_backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import bran.bran_backend.enums.State;
import bran.bran_backend.enums.Status;
import bran.bran_backend.models.User;
import bran.bran_backend.repositories.UserRepository;

/**
 *
 * @author bransmartuk
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        try {
            logger.info("Getting all users");
            List<User> users = this.userRepository.findAll();
            return users;
        } catch (Exception e) {
            logger.error("Error in Repository trying to grab all users", e);
            return new ArrayList<>();
        }
    }

    public List<User> getPendingUsers() {
        List<User> pendingUsers = this.getUsers().stream().filter(Users -> Users.getStatus() == Status.PENDING).collect(Collectors.toList());
        return new ArrayList<>(pendingUsers);
    }

    public User getUserById(Long id) {
        User user = this.getUsers().stream().filter(acquiredUser -> Objects.equals(acquiredUser.getId(), id)).findFirst().orElse(null);
        return user;
    }

    public void activateUser(Long id) {
        User user = this.getUsers().stream().filter(acquiredUser -> Objects.equals(acquiredUser.getId(), id)).findFirst().orElse(null);
        if (user != null) {
            user.setState(State.ACTIVATED);
        } else {
            logger.error("Users not found");
        }
    }

    public void deactivateUser(Long id) {
        User user = this.getUsers().stream().filter(acquiredUser -> Objects.equals(acquiredUser.getId(), id)).findFirst().orElse(null);
        if (user != null) {
            user.setState(State.DEACTIVATED);
        } else {
            logger.error("Users not found");
        }
    }

    public void rejectUser(Long id) {
        User user = this.getUsers().stream().filter(acquiredUser -> Objects.equals(acquiredUser.getId(), id)).findFirst().orElse(null);
        if (user != null) {
            user.setStatus(Status.REJECTED);
        } else {
            logger.error("Users not found");
        }
    }

    public void updateUser(User newUser) {
        try {
            User oldUser = this.getUsers().stream().filter(Users -> Objects.equals(Users.getId(), newUser.getId())).findFirst().orElse(null);
        if (oldUser != null && !oldUser.equals(newUser)) {
            oldUser.setName(newUser.getName());
            oldUser.setAge(newUser.getAge());
            oldUser.setHairColor(newUser.getHairColor());
            oldUser.setStatus(newUser.getStatus());
            this.userRepository.save(oldUser);
        } else {
            logger.error("Users not found or no changes made");
        }
        } catch (Exception e) {
            logger.error("Error in Repository trying to update user", e);
        }
    }

    public User createUser(User newUser) {
        try {
            logger.info("Creating new user");
            User createdUser = this.userRepository.save(newUser);
            return createdUser;
        } catch (Exception e) {
            logger.error("Error in Repository trying to create new user", e);
            return null;
        }
    }
}
