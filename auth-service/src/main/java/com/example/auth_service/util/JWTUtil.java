package com.example.auth_service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.storage-days}")
    private int storageDays;

    public String generateToken(String email) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusDays(storageDays).toInstant());
        return JWT.create()
                .withSubject("User details")
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer("auth-service")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveEmail(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("auth-service")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("email").asString();
    }

}
