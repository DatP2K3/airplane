package com.airplane.schedule.controller;

import com.airplane.schedule.dto.ApiResponse;
import com.airplane.schedule.dto.request.*;
import com.airplane.schedule.dto.response.AuthenticationResponseDTO;
import com.airplane.schedule.dto.response.UserResponseDTO;
import com.airplane.schedule.service.AuthServiceImpl;
import com.airplane.schedule.service.UserService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authServiceImpl;
    private final UserService userService;

    @PostMapping("")
    ApiResponse<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        ApiResponse<UserResponseDTO> apiResponse = ApiResponse.<UserResponseDTO>builder()
                .data(userResponseDTO)
                .success(true)
                .code(201)
                .message("User created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
        return apiResponse;
    }

    @PostMapping("/token")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequestDTO request) {
        authServiceImpl.authenticate(request);
        return ResponseEntity.ok("Otp sent to email");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthenticationResponseDTO> verifyOtp(@RequestBody VerifyOtpRequestDTO verifyOtpRequestDTO){
        AuthenticationResponseDTO result = authServiceImpl.verifyOtp(verifyOtpRequestDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(HttpServletRequest request)
            throws ParseException, JOSEException {
        AuthenticationResponseDTO result = authServiceImpl.refreshToken(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, @RequestBody IntrospectRequestDTO refreshToken)
            throws ParseException, JOSEException {
        authServiceImpl.logout(request, refreshToken);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
        authServiceImpl.requestPasswordReset(email);
        return ResponseEntity.ok("Reset password link sent to email");
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody PasswordRequestDTO passwordRequestDTO) {
        authServiceImpl.resetPassword(token, passwordRequestDTO.getNewPassword());
        return ResponseEntity.ok("Password successfully reset");
    }
}