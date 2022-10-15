package io.github.dayal96.util;

import java.security.MessageDigest;

public class CryptoUtil {
  public static byte[] sha256(byte[] toHash) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      return digest.digest(toHash);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
