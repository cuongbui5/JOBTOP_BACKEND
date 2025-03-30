package com.example.jobs_top.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class JwtService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    private static final int SIX_HOURS_MILLISECOND = 1000 * 60 * 60 * 6;
    private static final String USER_CLAIM = "user";
    private static final String ISSUER = "auth0";
    private static final String SECRET_KEY = "DCAWFLEFUWQDAJGDSIDHADUGADAJSGDYSAFCHOAFYCFOE";

    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);



    public String createToken(String email) {
        var builder = JWT.create();

        builder.withClaim(USER_CLAIM, email);
        return builder
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .withExpiresAt(new Date(System.currentTimeMillis() + SIX_HOURS_MILLISECOND))//  + SIX_HOURS_MILLISECOND
                .sign(ALGORITHM);



    }


    public static DecodedJWT validate(String token) {
        var verifier = JWT.require(ALGORITHM)
                .withIssuer(ISSUER)
                .build();
        return verifier.verify(token);
    }


    public static String getEmailFromToken(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims().get(USER_CLAIM).asString();



    }


}
