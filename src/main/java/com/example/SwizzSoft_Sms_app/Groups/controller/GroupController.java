package com.example.SwizzSoft_Sms_app.Groups.controller;



import com.example.SwizzSoft_Sms_app.Groups.Entity.Group;
import com.example.SwizzSoft_Sms_app.Groups.repo.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    // Get all groups
    @GetMapping
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    // Get a group by ID
    @GetMapping("get/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable String id) {

        List<Group> groups = groupRepository.findByOrganisationGroup(id);
        return  ResponseEntity.ok(groups);
    }

    // Create a new group
    @PostMapping
    public String createGroup(@RequestBody Group group) {
        groupRepository.save(group);
        return "Created Group successfully";
    }

    // Update an existing group
    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable int id, @RequestBody Group groupDetails) {
        return groupRepository.findById(id)
                .map(group -> {
                   // group.setOrganisationGroup(groupDetails.getOrganisationGroup());
                    group.setGroupName(groupDetails.getGroupName());
                    return ResponseEntity.ok(groupRepository.save(group));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a group
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable int id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
