package com.example.SwizzSoft_Sms_app.Messagein.dbo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkMessageDTO {
    private Integer code; // Sender ID
    private List<String> phoneNumber; // Array of phone numbers
    private List<String> message; // Array of messages
}
