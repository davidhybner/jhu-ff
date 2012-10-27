package jhu.ff.helpers;

import javax.servlet.http.Cookie;

public class CookieHelper {
    public static String getCookieValue(Cookie[] cookies, String cookieName) {
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
