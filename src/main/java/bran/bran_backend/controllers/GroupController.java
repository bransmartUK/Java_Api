/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package bran.bran_backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import bran.bran_backend.models.Group;
import bran.bran_backend.services.GroupService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




/**
 *
 * @author bransmartuk
 */
@RestController
@RequestMapping("/api/group")
public class GroupController {
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    private final GroupService groupService;

    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<Group>> getGroups() {
        try {
            return ResponseEntity.ok(this.groupService.getGroups());
        } catch (Exception e) {
            logger.error("Error getting all users", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Group> getGroup(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(this.groupService.getGroupById(id));
        } catch (Exception e) {
            logger.error("Error getting all users:", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> putGroup(@PathVariable Long id, Group newGroup) {
        try {
            logger.info("Updating group: " + id);
            this.groupService.updateGroup(newGroup, id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error updating group", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        try {
            logger.info("Creating new group");
            Group createdGroup = groupService.createGroup(group);
            return ResponseEntity.ok(createdGroup);
        } catch (Exception e) {
            logger.error("Error creating group", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        try {
            logger.info("Deleting group: " + id);
            this.groupService.deleteGroup(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting group", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
