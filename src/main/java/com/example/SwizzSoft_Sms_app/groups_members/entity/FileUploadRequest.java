package com.example.SwizzSoft_Sms_app.groups_members.entity;

import com.example.SwizzSoft_Sms_app.Messagein.Base64File;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FileUploadRequest {

    private String orgGroupCode;
    private int groupId;
    private List<Base64File> files;

    // Getters and Setters


}
