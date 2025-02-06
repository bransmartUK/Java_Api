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
import bran.bran_backend.models.Users;
import bran.bran_backend.repositories.UsersRepository;

/**
 *
 * @author bransmartuk
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UsersRepository userRepository;
    public UserService(UsersRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<Users> GetUsers() {
        try {
            logger.info("Getting all users");
            List<Users> users = this.userRepository.findAll();
            return users;
        } catch (Exception e) {
            logger.error("Error in Repository trying to grab all users", e);
            return new ArrayList<>();
        }
    }

    public List<Users> GetPendingUsers() {
        List<Users> pendingUsers = this.GetUsers().stream().filter(Users -> Users.getStatus() == Status.PENDING).collect(Collectors.toList());
        return new ArrayList<>(pendingUsers);
    }

    public Users GetUserById(Long id) {
        Users Users = this.GetUsers().stream().filter(acquiredUser -> Objects.equals(acquiredUser.getId(), id)).findFirst().orElse(null);
        return Users;
    }

    public void ActivateUser(Long id) {
        Users Users = this.GetUsers().stream().filter(acquiredUser -> Objects.equals(acquiredUser.getId(), id)).findFirst().orElse(null);
        if (Users != null) {
            Users.setState(State.ACTIVATED);
        } else {
            logger.error("Users not found");
        }
    }

    public void DeactivateUser(Long id) {
        Users Users = this.GetUsers().stream().filter(acquiredUser -> Objects.equals(acquiredUser.getId(), id)).findFirst().orElse(null);
        if (Users != null) {
            Users.setState(State.DEACTIVATED);
        } else {
            logger.error("Users not found");
        }
    }

    public void RejectUser(Long id) {
        Users Users = this.GetUsers().stream().filter(acquiredUser -> Objects.equals(acquiredUser.getId(), id)).findFirst().orElse(null);
        if (Users != null) {
            Users.setStatus(Status.REJECTED);
        } else {
            logger.error("Users not found");
        }
    }

    public void UpdateUser(Users newUser) {
        Users oldUser = this.GetUsers().stream().filter(Users -> Objects.equals(Users.getId(), newUser.getId())).findFirst().orElse(null);
        if (oldUser != null) {
            oldUser.setName(newUser.getName());
            oldUser.setAge(newUser.getAge());
            oldUser.setHairColor(newUser.getHairColor());
            oldUser.setStatus(newUser.getStatus());
        } else {
            logger.error("Users not found");
        }
    }

    public Users CreateUser(Users newUser) {
        try {
            logger.info("Creating new user");
            Users createdUser = this.userRepository.save(newUser);
            return createdUser;
        } catch (Exception e) {
            logger.error("Error in Repository trying to create new user", e);
            return null;
        }
    }
}
