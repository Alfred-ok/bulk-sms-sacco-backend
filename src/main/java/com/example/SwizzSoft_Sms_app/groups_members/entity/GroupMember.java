package com.example.SwizzSoft_Sms_app.groups_members.entity;


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
@Table(name = "groups_members")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "org_group_code")
    private String orgGroupCode;

    @Column(name = "groupId")
    private int groupId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "names")
    private String names;



}

