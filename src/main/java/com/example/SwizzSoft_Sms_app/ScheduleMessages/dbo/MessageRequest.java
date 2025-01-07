package com.example.SwizzSoft_Sms_app.ScheduleMessages.dbo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
public class MessageRequest {
    private String message;
    private List<String> phoneNumbers; // Array of phone numbers
    private Integer code; // Organisation code
    private LocalDate date;
    private LocalTime time;
    private String frequency;
}
