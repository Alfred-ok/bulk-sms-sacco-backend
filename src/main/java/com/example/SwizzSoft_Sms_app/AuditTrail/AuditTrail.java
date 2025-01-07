package com.example.SwizzSoft_Sms_app.AuditTrail;

import jakarta.persistence.GeneratedValue;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audit_trail")
public class AuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String person;

    @Column(nullable = false)
    private String activity;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "groupID")
    private Integer groupID;



}
