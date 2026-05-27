package com.dashboard.saas.security;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.encryptkey}")
    private String jwtEncryptionKey;

    @Value("${app.jwt.access-token-expiration-ms}")
    private Long jwtAccessTokenExpirationInMs;

    @Value("${app.jwt.refresh-token-expiration-ms}")
    private Long jwtRefreshTokenExpirationInMs;



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

    public Long getJwtRefreshTokenExpirationInMs() {
        return jwtRefreshTokenExpirationInMs;
    }

    public void setJwtRefreshTokenExpirationInMs(Long jwtRefreshTokenExpirationInMs) {
        this.jwtRefreshTokenExpirationInMs = jwtRefreshTokenExpirationInMs;
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

            JWSSigner signer = new MACSigner(jwtSecret);
            signedJWT.sign(signer);

            // STEP 3 → Encrypt JWt
            JWEObject jweObject = new JWEObject(new JWEHeader.Builder(JWEAlgorithm.DIR,
                    EncryptionMethod.A256CBC_HS512).contentType("JWT").build(),
                    new Payload(signedJWT));

            //step 4 → Encrypt JWT using base 64 and A256GCM encryption Algorithm


            byte[] encryptionKeyBytes = Base64.getDecoder().decode(jwtEncryptionKey);
            DirectEncrypter encrypter = new DirectEncrypter(encryptionKeyBytes);

            if (encryptionKeyBytes.length != 64) {
                throw new IllegalArgumentException("Encryption key must be 256 bits (32 bytes) long when using A256GCM encryption method.");
            }
            JWEEncrypter encrypters =
                    new DirectEncrypter(encryptionKeyBytes);

            jweObject.encrypt(encrypter);

            return jweObject.serialize();
        } catch (Exception e) {
            throw new RuntimeException("Error generating JWT token: " + e.getMessage(),

                    e);
        }


    }

    public String generateRefreshToken(Long userId) {

        try {

            Date now = new Date();

            Date expiryDate =
                    new Date(now.getTime() + jwtRefreshTokenExpirationInMs);

            // CLAIMS
            JWTClaimsSet claimsSet =
                    new JWTClaimsSet.Builder()
                            .subject(String.valueOf(userId))
                            .issueTime(now)
                            .expirationTime(expiryDate)
                            .build();

            // SIGN JWT
            SignedJWT signedJWT =
                    new SignedJWT(
                            new JWSHeader(JWSAlgorithm.HS256),
                            claimsSet
                    );

            JWSSigner signer =
                    new MACSigner(jwtSecret);

            signedJWT.sign(signer);

            // ENCRYPT JWT
            JWEObject jweObject =
                    new JWEObject(
                            new JWEHeader.Builder(
                                    JWEAlgorithm.DIR,
                                    EncryptionMethod.A256CBC_HS512
                            ).contentType("JWT").build(),

                            new Payload(signedJWT)
                    );

            byte[] encryptionKey =
                    Base64.getDecoder()
                            .decode(jwtEncryptionKey);

            jweObject.encrypt(
                    new DirectEncrypter(encryptionKey)
            );

            return jweObject.serialize();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error generating refresh token",
                    e
            );
        }
    }
    //validate token

    public boolean validateToken(String token) {

        try {
            decryptAndVerify(token);

            return true;

        } catch (Exception e) {
            throw new RuntimeException("Error validating JWT token: " + e.getMessage(), e);
        }
    }



    public Long userIdFromToken(String token) {

        try {
            JWTClaimsSet claimsSet = decryptAndVerify(token);

//            String userIdStr = claimsSet.getSubject();
            return Long.valueOf(claimsSet.getSubject());

        } catch (Exception e) {
            throw new RuntimeException("Error extracting user ID from JWT token: " + e.getMessage(), e);
        }
    }

    public String emailFromToken(String token) {

        try {
            JWTClaimsSet claimsSet = decryptAndVerify(token);

            return claimsSet.getStringClaim("email");


        } catch (Exception e) {
            throw new RuntimeException("Error extracting email from JWT token: " + e.getMessage(), e);
        }
    }

    public  String fullNameFromToken(String token) {

        try {
            JWTClaimsSet claimsSet = decryptAndVerify(token);

            return claimsSet.getStringClaim("fullName");

        } catch (Exception e) {
            throw new RuntimeException("Error extracting full name from JWT token: " + e.getMessage(), e);
        }

    }

    public String getTokenSubject(String token) {

        try {
            JWTClaimsSet claimsSet = decryptAndVerify(token);

            return claimsSet.getSubject();

        } catch (Exception e) {
            throw new RuntimeException("Error extracting subject from JWT token: " + e.getMessage(), e);
        }
    }

    public Date getTokenExpiration(String token) {

        try {
            JWTClaimsSet claimsSet = decryptAndVerify(token);

            return claimsSet.getExpirationTime();

        } catch (Exception e) {
            throw new RuntimeException("Error extracting expiration time from JWT token: " + e.getMessage(), e);
        }
    }
    public Date getTokenIssueTime(String token) {

        try {
            JWTClaimsSet claimsSet = decryptAndVerify(token);

            return claimsSet.getIssueTime();

        } catch (Exception e) {
            throw new RuntimeException("Error extracting issue time from JWT token: " + e.getMessage(), e);
        }
    }

    public JWTClaimsSet getAllClaimsFromToken(String token) {

        try {
            return decryptAndVerify(token);

        } catch (Exception e) {
            throw new RuntimeException("Error extracting claims from JWT token: " + e.getMessage(), e);
        }
    }


    /**
     * INTERNAL METHOD
     */
    private JWTClaimsSet decryptAndVerify(
            String encryptedToken
    ) {

        try {

            // STEP 1 → PARSE TOKEN
            JWEObject jweObject =
                    JWEObject.parse(encryptedToken);

            // STEP 2 → DECRYPT TOKEN
            byte[] encryptionKey =
                    Base64.getDecoder()
                            .decode(jwtEncryptionKey);

            jweObject.decrypt(
                    new DirectDecrypter(encryptionKey)
            );

            // STEP 3 → EXTRACT SIGNED JWT
            SignedJWT signedJWT =
                    jweObject.getPayload()
                            .toSignedJWT();

            // STEP 4 → VERIFY SIGNATURE
            JWSVerifier verifier =
                    new MACVerifier(jwtSecret);

            boolean verified =
                    signedJWT.verify(verifier);

            if (!verified) {

                throw new RuntimeException(
                        "Invalid JWT Signature"
                );
            }

            // STEP 5 → GET CLAIMS
            JWTClaimsSet claimsSet =
                    signedJWT.getJWTClaimsSet();

            // STEP 6 → CHECK EXPIRY
            if (claimsSet.getExpirationTime()
                    .before(new Date())) {

                throw new RuntimeException(
                        "Token Expired"
                );
            }

            return claimsSet;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Invalid Token",
                    e
            );
        }
    }
}





