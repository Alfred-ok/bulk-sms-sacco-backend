package com.example.SwizzSoft_Sms_app.ScheduleMessages.dbo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkMessageDTO {
    private Integer code; // Sender ID
    private List<String> phoneNumber; // Array of phone numbers
    private List<String> message; // Array of messages
    private LocalDate date;
    private LocalTime time;
    private String frequency;

}
