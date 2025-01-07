package com.example.SwizzSoft_Sms_app.Users;

import org.springframework.stereotype.Service;


import okhttp3.*;

import java.io.IOException;

@Service
public class PasswordService {

    private final OkHttpClient client = new OkHttpClient();

    public String resetPassword(ResetPasswordDTO resetPasswordDTO) throws IOException {
        String url = "http://138.201.58.10:9001/api/Auth/Resetpassword";

        // Convert DTO to JSON
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String jsonPayload = "{"
                + "\"userName\":\"" + resetPasswordDTO.getUserName() + "\","
                + "\"oldPassword\":\"" + resetPasswordDTO.getOldPassword() + "\","
                + "\"password\":\"" + resetPasswordDTO.getPassword() + "\","
                + "\"confirmPassword\":\"" + resetPasswordDTO.getConfirmPassword() + "\""
                + "}";

        // Create Request Body
        RequestBody body = RequestBody.create(jsonPayload, JSON);

        // Build Request
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // Execute Request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response.code() + ": " + response.body().string());
            }

            return response.body().string();
        }
    }
}
