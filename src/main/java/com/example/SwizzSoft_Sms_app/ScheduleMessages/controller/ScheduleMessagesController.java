package com.example.SwizzSoft_Sms_app.ScheduleMessages.controller;


import com.example.SwizzSoft_Sms_app.Messagein.Base64File;
import com.example.SwizzSoft_Sms_app.ScheduleMessages.dbo.BulkMessageDTO;
import com.example.SwizzSoft_Sms_app.ScheduleMessages.dbo.FileUploadRequest;
import com.example.SwizzSoft_Sms_app.ScheduleMessages.dbo.MessageRequest;
import com.example.SwizzSoft_Sms_app.ScheduleMessages.dbo.ScheduleMessages;
import com.example.SwizzSoft_Sms_app.ScheduleMessages.repo.ScheduleMessagesRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RequestMapping("schedule_messages")
@RestController
public class ScheduleMessagesController {

    @Autowired
    private ScheduleMessagesRepository repository;

    // Get all messages
    @GetMapping
    public List<ScheduleMessages> getAllMessages() {
        return repository.findAll();
    }


/*
    // Create a new message
    @PostMapping
    public ScheduleMessages createMessage(@RequestBody ScheduleMessages scheduleMessages) {
        return repository.save(scheduleMessages);
    }

*/

    // POST endpoint to save a new message
    @PostMapping("/sendsms")
    public ResponseEntity<String> createMessage(@RequestBody ScheduleMessages scheduleMessages) {


        //auditDate
        LocalDateTime now = LocalDateTime.now();

        // Convert comma-separated string to ArrayList
        List<String> arrayList = new ArrayList<>(Arrays.asList(scheduleMessages.getPhoneNumber().split(",")));

        // Optionally, trim whitespace from each element
        arrayList.replaceAll(String::trim);

        //post all data repeatedly after every phone number
        for (String eachPhoneNumber : arrayList) {

            ScheduleMessages message = new ScheduleMessages();
            message.setMessage(scheduleMessages.getMessage());
            //auditDate
            message.setAuditDate(now);
            //each
            message.setPhoneNumber(eachPhoneNumber);
            message.setSendStatus(scheduleMessages.getSendStatus());
            message.setMsgStatus(scheduleMessages.getMsgStatus());
            //organisation code
            message.setCode(scheduleMessages.getCode());

            message.setDate(scheduleMessages.getDate());
            message.setTime(scheduleMessages.getTime());
            message.setFrequency(scheduleMessages.getFrequency());

            repository.save(message);
        }

        return ResponseEntity.ok("Successfully");
    }







    @PostMapping("/sendSmsByFile")
    public ResponseEntity<String> uploadFile(@RequestBody FileUploadRequest request) {
        if (request.getFiles() == null || request.getFiles().isEmpty()) {
            return ResponseEntity.badRequest().body("No files were uploaded. Please upload a valid file.");
        }

        List<ScheduleMessages> scheduleMessages = new ArrayList<>();

        try {
            for (Base64File file : request.getFiles()) {
                // Validate Base64 data format
                if (file.getData() == null || !file.getData().contains(",")) {
                    return ResponseEntity.badRequest().body("Invalid file data for file: " + file.getFilename());
                }

                byte[] decodedBytes = Base64.getDecoder().decode(file.getData().split(",")[1]);
                try (InputStream inputStream = new ByteArrayInputStream(decodedBytes);
                     Workbook workbook = WorkbookFactory.create(inputStream)) {

                    Sheet sheet = workbook.getSheetAt(0);
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header row
                        Row row = sheet.getRow(i);
                        if (row == null) continue;

                        String phoneNumber = getStringCellValue(row.getCell(0));
                        String message = getStringCellValue(row.getCell(1));

                        if (phoneNumber == null || message == null) {
                            // Skip rows with incomplete data
                            continue;
                        }

                        ScheduleMessages scheduleMessage = ScheduleMessages.builder()
                                .code(request.getOrgCode())
                                .phoneNumber(phoneNumber)
                                .message(message)
                                .msgStatus("0")
                                .sendStatus("NOT SENT")
                                .auditDate(LocalDateTime.now())
                                .date(request.getDate())
                                .time(request.getTime())
                                .frequency(request.getFrequency())
                                .build();

                        scheduleMessages.add(scheduleMessage);
                    }
                }
            }

            // Save all records to the database
            repository.saveAll(scheduleMessages);
            return ResponseEntity.ok("Files uploaded and data saved successfully.");

        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to upload file: " + e.getMessage());
        }
    }





    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return null;
        }
    }










    //sending message on basis of group
    @PostMapping("/group-sms")
    public ResponseEntity<String> groupMessage(@RequestBody MessageRequest messagesIn) {

        // Audit date
        LocalDateTime now = LocalDateTime.now();


        // Validate and process the phone numbers
        if (messagesIn.getPhoneNumbers() == null || messagesIn.getPhoneNumbers().isEmpty()) {
            return ResponseEntity.badRequest().body("Phone numbers are required.");
        }


        // Loop through each phone number and create a Messagein entity
        for (String phoneNumber : messagesIn.getPhoneNumbers()) {
            // Trim the phone number to remove extra spaces
            phoneNumber = phoneNumber.trim();

            // Create a new message entity for each phone number
            ScheduleMessages message = new ScheduleMessages();
            message.setMessage(messagesIn.getMessage());
            message.setAuditDate(now);
            message.setPhoneNumber(phoneNumber);
            message.setSendStatus("NOT SENT");
            message.setMsgStatus("0");
            message.setCode(messagesIn.getCode());
            message.setDate(messagesIn.getDate());
            message.setTime(messagesIn.getTime());
            message.setFrequency(messagesIn.getFrequency());

            // Save to the database
            repository.save(message);
        }

        return ResponseEntity.ok("Successfully");
    }









    @PostMapping("/group-smsBy-file")
    public ResponseEntity<String> groupMessage(@RequestBody ScheduleMessages messagesIn) {

        //auditDate
        LocalDateTime now = LocalDateTime.now();

        // Convert comma-separated string to ArrayList
        List<String> arrayList = new ArrayList<>(Arrays.asList(messagesIn.getPhoneNumber().split(",")));



        //post all data repeatedly after every phone number
        for (String eachPhoneNumber : arrayList) {

            ScheduleMessages message = new ScheduleMessages();
            message.setMessage(messagesIn.getMessage());
            //auditDate
            message.setAuditDate(now);
            //each
            message.setPhoneNumber(eachPhoneNumber);
            message.setSendStatus("NOT SENT");
            message.setMsgStatus("0");
            //organisation code
            message.setCode(messagesIn.getCode());

            message.setDate(messagesIn.getDate());
            message.setTime(messagesIn.getTime());
            message.setFrequency(messagesIn.getFrequency());
            repository.save(message);
        }

        return ResponseEntity.ok("Successfully");
    }











    @PostMapping("/group-sms-file-custom")
    public ResponseEntity<String> groupMessageFileCustom(@RequestBody BulkMessageDTO bulkMessageDTO) {

        // Validate that the number of phone numbers matches the number of messages
        if (bulkMessageDTO.getPhoneNumber().size() != bulkMessageDTO.getMessage().size()) {
            return ResponseEntity.badRequest().body("Phone numbers and messages arrays must have the same size.");
        }

        // Audit date
        LocalDateTime now = LocalDateTime.now();

        // Iterate over the phone numbers and messages
        for (int i = 0; i < bulkMessageDTO.getPhoneNumber().size(); i++) {
            String eachPhoneNumber = bulkMessageDTO.getPhoneNumber().get(i);
            String eachMessage = bulkMessageDTO.getMessage().get(i);

            // Map data to the Messagein entity
            ScheduleMessages message = new ScheduleMessages();
            message.setPhoneNumber(eachPhoneNumber);
            message.setMessage(eachMessage);
            message.setAuditDate(now);
            message.setSendStatus("NOT SENT");
            message.setMsgStatus("0");
            message.setCode(bulkMessageDTO.getCode());
            message.setDate(bulkMessageDTO.getDate());
            message.setTime(bulkMessageDTO.getTime());
            message.setFrequency(bulkMessageDTO.getFrequency());

            // Save each message
            repository.save(message);
        }

        return ResponseEntity.ok("Successfully processed all messages.");
    }


















    // Delete a message
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
/*
    @GetMapping("/get_code/{code}")
    public ResponseEntity<List<ScheduleMessages>> getByCode(
            @PathVariable Integer code,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "200") int size) {
        // Sort by "auditDate" in descending order
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "auditDate"));

        List<ScheduleMessages> messages = repository.findByCode(code, pageable).getContent();

        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
    }

 */






    @GetMapping("/get_code/{code}")
    public ResponseEntity<List<ScheduleMessages>> getByCode(
            @PathVariable Integer code,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "200") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        // Sort by "auditDate" in descending order
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "auditDate"));

        // Fetch messages based on code and optional date range
        Page<ScheduleMessages> result;
        if (fromDate != null && toDate != null) {
            // Filter messages by code and date range
            result = repository.findByCodeAndAuditDateBetween(code, fromDate.atStartOfDay(), toDate.atTime(23, 59, 59), pageable);
        } else {
            // Filter messages by code only
            result = repository.findByCode(code, pageable);
        }

        List<ScheduleMessages> messages = result.getContent();

        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
        
    }






















}
