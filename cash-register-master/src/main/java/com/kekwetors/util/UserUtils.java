package com.kekwetors.util;

import com.kekwetors.dao.model.Employee;
import lombok.SneakyThrows;

import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class UserUtils {

  private static int REDIRECT_ID = 0;

  private static final Map<Integer, String> idUriMap = new HashMap();
  private static final Map<String, Integer> uriIdMap = new HashMap();

  public static void storeLoggedInUser(HttpSession session, Employee loggedInUser) {
    session.setAttribute("loggedInUser", loggedInUser);
  }

  public static Employee getLoggedInUser(HttpSession session) {
    return (Employee) session.getAttribute("loggedInUser");
  }

  public static int storeRedirectAfterLoginUrl(HttpSession session, String requestUri) {
    Integer id = uriIdMap.get(requestUri);

    if (id == null) {
      id = REDIRECT_ID++;

      uriIdMap.put(requestUri, id);
      idUriMap.put(id, requestUri);
      return id;
    }
    return id;
  }

  public static String getRedirectAfterLoginUrl(HttpSession session, int redirectId) {
    String url = idUriMap.get(redirectId);
    if (url != null) {
      return url;
    }
    return null;
  }

  @SneakyThrows
  public static String getShaHash(String message) {
    MessageDigest sha1 = MessageDigest.getInstance("SHA1");
    byte[] result = sha1.digest(message.getBytes());
    return bytesToHexString(result);
  }

  public static String bytesToHexString(byte[] bytes){
    StringBuffer sb = new StringBuffer();
    for (byte b : bytes) {
      sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
  }
}
