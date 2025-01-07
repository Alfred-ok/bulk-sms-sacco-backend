package com.example.SwizzSoft_Sms_app.Groups.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;

    private String organisationGroup;
    private String groupName;

    // Getters and Setters
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getOrganisationGroup() {
        return organisationGroup;
    }

    public void setOrganisationGroup(String organisationGroup) {
        this.organisationGroup = organisationGroup;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

