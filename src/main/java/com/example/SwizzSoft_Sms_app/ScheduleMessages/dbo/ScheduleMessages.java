package com.example.SwizzSoft_Sms_app.ScheduleMessages.dbo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ScheduleMessages", schema = "dbo")
public class ScheduleMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Code", nullable = false, length = 50)
    private Integer code;

    @Column(name = "PhoneNumber", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "Message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "msgstatus", nullable = false, length = 20)
    private String msgStatus;

    @Column(name = "SendStatus", nullable = false, length = 20)
    private String sendStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AuditDate", nullable = false)
    private LocalDateTime auditDate;

    // @Column(name = "FID")
   // private Integer fid; // Allows null

   // @Column(name = "ResponseStatus", length = 50)
   // private String responseStatus; // Allows null

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Column(name = "Time", nullable = false)
    private LocalTime time;

    @Column(name = "Frequency", nullable = false, length = 50)
    private String frequency;
}
