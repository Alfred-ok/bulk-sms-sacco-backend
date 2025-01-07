package com.example.SwizzSoft_Sms_app.ScheduleMessages.dbo;

import com.example.SwizzSoft_Sms_app.Messagein.Base64File;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class FileUploadRequest {
    private Integer orgCode;
    private List<Base64File> files;

    private LocalDate date;
    private LocalTime time;
    private String frequency;

    // Getters and Setters


}
