package com.airplane.schedule.service;


import com.airplane.schedule.dto.request.AuthenticationRequestDTO;
import com.airplane.schedule.dto.request.IntrospectRequestDTO;
import com.airplane.schedule.dto.request.VerifyOtpRequestDTO;
import com.airplane.schedule.dto.response.AuthenticationResponseDTO;
import com.airplane.schedule.dto.response.IntrospectResponseDTO;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;

public interface AuthService {

    IntrospectResponseDTO introspect(IntrospectRequestDTO request) throws JOSEException, ParseException;

    void authenticate(AuthenticationRequestDTO request);

    AuthenticationResponseDTO verifyOtp(VerifyOtpRequestDTO verifyOtpRequestDTO);

    void logout(HttpServletRequest request, IntrospectRequestDTO refreshToken) throws ParseException, JOSEException;

    AuthenticationResponseDTO refreshToken(HttpServletRequest request) throws ParseException, JOSEException;

    void requestPasswordReset(String email);

    void resetPassword(String token, String newPassword);
}