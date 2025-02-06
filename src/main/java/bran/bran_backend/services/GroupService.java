/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package bran.bran_backend.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import bran.bran_backend.models.Group;
import bran.bran_backend.repositories.GroupRepository;

/**
 *
 * @author bransmartuk
 */
@Service
public class GroupService {
    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
        
    }

    public List<Group> getGroups() {
        return this.groupRepository.findAll();
    }

    public Group getGroupById(Long id) {
        return this.groupRepository.findById(id).orElse(null);
    }

    public void updateGroup(Group newGroup, Long id) {
        try {
            Group group = this.getGroupById(id);
            if (group != null) {
                group.setName(newGroup.getName());
                group.setDescription(newGroup.getDescription());
                group.setLocation(newGroup.getLocation());
                this.groupRepository.save(group);
                logger.info("Group Updated: " + id);
            } else {
                logger.atWarn().log("Group not found: " + id);
            }
        } catch (Exception e) {
            logger.error("Error trying to update Group:", e);
        }
    }

    public Group createGroup(Group group) {
        try {
            this.groupRepository.save(group);
            logger.info("Group Created: " + group.getId());
            return group;
        } catch (Exception e) {
            logger.error("Error trying to create Group:", e);
            return null;
        }
    }

    public void deleteGroup(Long id) {
        try {
            this.groupRepository.deleteById(id);
            logger.info("Group Deleted: " + id);
        } catch (Exception e) {
            logger.error("Error trying to delete Group:", e);
        }
    }


}
