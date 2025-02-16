package com.airplane.schedule.config.security;

import com.airplane.schedule.dto.request.IntrospectRequestDTO;
import com.airplane.schedule.service.Impl.AuthServiceImpl;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {
    private final AuthServiceImpl authServiceImpl;
    private NimbusJwtDecoder nimbusJwtDecoder = null;
    private final TokenProvider tokenProvider;

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            var response = authServiceImpl.introspect(
                    IntrospectRequestDTO.builder().token(token).build());

            if (!response.isValid()) throw new JwtException("Token invalid");
        } catch (JOSEException | ParseException e) {
            throw new JwtException(e.getMessage());
        }

        if (Objects.isNull(nimbusJwtDecoder)) {
            nimbusJwtDecoder = NimbusJwtDecoder.withPublicKey((RSAPublicKey) tokenProvider.getKeyPair().getPublic()).build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}