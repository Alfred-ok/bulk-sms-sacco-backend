package com.example.SwizzSoft_Sms_app.Users;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordDTO {

    // Getters and setters
    private String userName;
    private String oldPassword;
    private String password;
    private String confirmPassword;

}
