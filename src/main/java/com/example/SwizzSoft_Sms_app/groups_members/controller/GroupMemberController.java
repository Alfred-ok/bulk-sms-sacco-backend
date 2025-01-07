package com.example.SwizzSoft_Sms_app.groups_members.controller;

import com.example.SwizzSoft_Sms_app.Messagein.Base64File;
import com.example.SwizzSoft_Sms_app.groups_members.entity.FileUploadRequest;
import com.example.SwizzSoft_Sms_app.groups_members.entity.GroupMember;
import com.example.SwizzSoft_Sms_app.groups_members.repo.GroupMemberRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/group-members")
public class GroupMemberController {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    // Get all group members
    @GetMapping
    public List<GroupMember> getAllGroupMembers() {
        return groupMemberRepository.findAll();
    }

    // Get group members by group code
    @GetMapping("/by-org-group-code/{orgGroupCode}")
    public List<GroupMember> getGroupMembersByOrgGroupCode(@PathVariable String orgGroupCode) {
        return groupMemberRepository.findByOrgGroupCode(orgGroupCode);
    }

    // Get a group member by ID
    @GetMapping("/{id}")
    public ResponseEntity<GroupMember> getGroupMemberById(@PathVariable int id) {
        return groupMemberRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getGroup{groupId}")
    public ResponseEntity<?> getGroupMembersByGroupId(@PathVariable int groupId) {
        List<GroupMember> members = groupMemberRepository.findByGroupId(groupId);
        if (members.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content if no members found
        }
        return ResponseEntity.ok(members); // 200 OK with the list of members
    }


    // Create a new group member
    @PostMapping
    public String createGroupMember(@RequestBody GroupMember groupMember) {
        groupMemberRepository.save(groupMember);
        return "Succesfully Added";
    }

    // Update an existing group member
    @PutMapping("/{id}")
    public ResponseEntity<GroupMember> updateGroupMember(@PathVariable int id, @RequestBody GroupMember groupMemberDetails) {
        return groupMemberRepository.findById(id)
                .map(groupMember -> {
                    groupMember.setOrgGroupCode(groupMemberDetails.getOrgGroupCode());
                    groupMember.setPhoneNumber(groupMemberDetails.getPhoneNumber());
                    groupMember.setGroupId(groupMemberDetails.getGroupId());
                    groupMember.setNames(groupMemberDetails.getNames());
                    return ResponseEntity.ok(groupMemberRepository.save(groupMember));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a group member
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupMember(@PathVariable int id) {
        if (groupMemberRepository.existsById(id)) {
            groupMemberRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

























    @PostMapping("/group-file")
    public ResponseEntity<String> uploadFile(@RequestBody FileUploadRequest request) {
        try {
            if (request.getFiles() == null || request.getFiles().isEmpty()) {
                return ResponseEntity.badRequest().body("No files were uploaded. Please upload a valid file.");
            }

            List<GroupMember> groupMembers = new ArrayList<>();

            // Loop through each uploaded file
            for (Base64File file : request.getFiles()) {
                byte[] decodedBytes = Base64.getDecoder().decode(file.getData().split(",")[1]); // Remove 'data:<type>;base64,' prefix
                InputStream inputStream = new ByteArrayInputStream(decodedBytes);
                Workbook workbook = WorkbookFactory.create(inputStream);
                Sheet sheet = workbook.getSheetAt(0);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header row
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    GroupMember groupMember = GroupMember.builder()
                            .groupId(request.getGroupId())
                            .orgGroupCode(request.getOrgGroupCode())
                            .names(getStringCellValue(row.getCell(0)))
                            .phoneNumber(getStringCellValue(row.getCell(1)))
                            .build();

                    groupMembers.add(groupMember);
                }
                workbook.close();
            }

            // Save all records to the database
            groupMemberRepository.saveAll(groupMembers);
            return ResponseEntity.ok("Files uploaded and data saved successfully.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload file: " + e.getMessage());
        }
    }




    private String getStringCellValue(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue()); // Convert numeric to string if needed
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }

    private double getNumericCellValue(Cell cell) {
        if (cell == null) return 0;
        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> Double.parseDouble(cell.getStringCellValue()); // Attempt to parse string as numeric
            default -> 0;
        };
    }
}
