package com.example.SwizzSoft_Sms_app.Users;

import com.example.SwizzSoft_Sms_app.SecurityAndJwt.dto.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordService passwordService;

    @GetMapping("/get_User")
    public ResponseEntity<Object> getUser() {
        List<AspNetUsers> users = repo.findAllByOrderByIdDesc();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/group/{groupID}")
    public ResponseEntity<List<AspNetUsers>> getUsersByGroupID(@PathVariable String groupID) {
        List<AspNetUsers> users = repo.findByGroupID(groupID);
        return ResponseEntity.ok(users);
    }


    @PutMapping("/update_User/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable String id, @RequestBody AspNetUsers updatedUser) {
        // Check if the user exists
        Optional<AspNetUsers> existingUserOptional = repo.findById(id);
        if (!existingUserOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found.");
        }

        // Update the user
        AspNetUsers existingUser = existingUserOptional.get();
        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setNames(updatedUser.getNames());
        existingUser.setOrganisation(updatedUser.getOrganisation());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setDisabled(updatedUser.getDisabled());
        existingUser.setGroupID(updatedUser.getGroupID());

        // Save the updated user
        repo.save(existingUser);

        return ResponseEntity.ok("User with ID " + id + " has been updated.");
    }


    @DeleteMapping("/delete_User/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.ok("User with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found.");
        }
    }


    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        try {
            String response = passwordService.resetPassword(resetPasswordDTO);
            // Parse the string to remove escape characters
            ObjectMapper tempMapper = new ObjectMapper();
            String parsedJson = tempMapper.readTree(response).asText();

            System.out.println("Parsed JSON String: " + parsedJson);

            // Deserialize the cleaned JSON string into the LoginResponse class
           // ObjectMapper mapper = new ObjectMapper();
           // ResetPasswordResponse resetPasswordResponse = mapper.readValue(parsedJson, ResetPasswordResponse.class);

            return ResponseEntity.ok(parsedJson);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }




    @GetMapping("findUser/{userName}")
    public ResponseEntity<AspNetUsers> getUserByUserName(@PathVariable String userName) {
        return repo.findByUserName(userName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
