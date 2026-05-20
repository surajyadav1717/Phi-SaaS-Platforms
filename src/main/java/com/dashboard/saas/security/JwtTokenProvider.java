package com.dashboard.saas.security;


import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.encryptkey}")
    private String jwtEncryptionKey;

    @Value("${app.jwt.access-token-expiration-ms}")
    private Long jwtAccessTokenExpirationInMs;


    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String getJwtEncryptionKey() {
        return jwtEncryptionKey;
    }

    public void setJwtEncryptionKey(String jwtEncryptionKey) {
        this.jwtEncryptionKey = jwtEncryptionKey;
    }

    public Long getJwtAccessTokenExpirationInMs() {
        return jwtAccessTokenExpirationInMs;
    }

    public void setJwtAccessTokenExpirationInMs(Long jwtAccessTokenExpirationInMs) {
        this.jwtAccessTokenExpirationInMs = jwtAccessTokenExpirationInMs;
    }


    // Generate JWT token, validate token, extract claims, etc. can be implemented here as needed.

    public String generateToken(Long userId,
                                String email,
                                String fullName
    ) {

        try {

            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + jwtAccessTokenExpirationInMs);

            //Jwt Claims add here

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(String.valueOf(userId))
                    .claim("email", email)
                    .claim("fullName", fullName)
                    .issueTime(now)
                    .expirationTime(expiryDate)
                    .build();


            // STEP 2 → SIGN JWT
            SignedJWT signedJWT =
                    new SignedJWT(
                            new JWSHeader(JWSAlgorithm.HS256),
                            claimsSet
                    );



        } catch (Exception e) {
            e.printStackTrace();
            return email;
        }

        return email;
    }
}
