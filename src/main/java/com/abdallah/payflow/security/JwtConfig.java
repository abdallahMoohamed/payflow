package com.abdallah.payflow.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfig {

    @Value("${JWT_PRIVATE_KEY}")
    private String privateKeyValue;

    @Value("${JWT_PUBLIC_KEY}")
    private String publicKeyValue;

    @Bean
    public KeyPair jwtKeyPair() throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        byte[] privateBytes = Base64.getDecoder().decode(privateKeyValue);
        byte[] publicBytes = Base64.getDecoder().decode(publicKeyValue);

        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicBytes));

        return new KeyPair(publicKey, privateKey);
    }
}