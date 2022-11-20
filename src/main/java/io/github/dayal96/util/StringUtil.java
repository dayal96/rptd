package io.github.dayal96.util;

public class StringUtil {
  
  public static String removeQuotes(String str) {
    return str.substring(1,str.length() - 1).replaceAll("\"", "\"").replaceAll("\\\\", "\\");
  }
}
