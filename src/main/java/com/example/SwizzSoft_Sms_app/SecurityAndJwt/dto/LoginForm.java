package com.example.SwizzSoft_Sms_app.SecurityAndJwt.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginForm {
    private String username;
    private String password;


}
