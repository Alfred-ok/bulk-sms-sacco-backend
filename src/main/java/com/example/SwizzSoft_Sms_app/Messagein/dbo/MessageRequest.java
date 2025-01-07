package com.example.SwizzSoft_Sms_app.Messagein.dbo;


import java.util.List;

public class MessageRequest {
    private String message;
    private List<String> phoneNumbers; // Array of phone numbers
    private Integer code; // Organisation code

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
