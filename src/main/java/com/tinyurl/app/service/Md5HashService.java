package com.tinyurl.app.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class Md5HashService implements HashService {

    public String generateHash( String url) throws NoSuchAlgorithmException {

      String hashtext = null;
      MessageDigest md = MessageDigest.getInstance("MD5");

      // Compute message digest of the input
      byte[] messageDigest = md.digest(url.getBytes());

      hashtext = convertToHex(messageDigest);

      return hashtext;
   }

   private String convertToHex(final byte[] messageDigest) {
    BigInteger bigint = new BigInteger(1, messageDigest);
    String hexText = bigint.toString(16);
    while (hexText.length() < 32) {
       hexText = "0".concat(hexText);
    }
    return hexText;
 }
}