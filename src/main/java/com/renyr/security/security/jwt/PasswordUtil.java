package com.renyr.security.security.jwt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    /**
     * 密码加密
     * @param password 要加密的密码
     * @return
     */
    public static String encodePassword(String password) {
        if (password == null || "".equals(password.trim()))
            return password;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password.trim());
    }

    /**
     * 密码匹配
     * @param rawPassword 要比对的密码
     * @param encodedPassword 加密后的密码
     * @return true表示比对成功 false表比对失败
     */
    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword,encodedPassword);
    }

}
