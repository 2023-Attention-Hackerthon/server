package com.hackathon.hackathon.global.utils;

import com.hackathon.hackathon.domain.user.exception.PublicKeyGenerationException;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class PublicKeyGenerator {
    public static RSAPublicKey excute(String kty, String n, String e) {
        BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(n));
        BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(e));

        try{
            KeyFactory keyFactory = KeyFactory.getInstance(kty);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception){
            throw new PublicKeyGenerationException();
        }
    }
}