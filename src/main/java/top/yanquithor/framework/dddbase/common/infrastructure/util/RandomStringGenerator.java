package top.yanquithor.framework.dddbase.common.infrastructure.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * Random string generator utility class that creates cryptographically secure random strings.
 * Provides methods to generate random strings of specified length or a default length.
 * Useful for generating unique identifiers, tokens, and other random string values.
 *
 * @author YanQuithor
 * @since 2025-10-29
 */
public class RandomStringGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final SecureRandom random = new SecureRandom(); // 安全随机数生成器 - 安全随机数生成器

    /**
     * Generates a random string of the specified length.
     * Uses a cryptographically secure random number generator for better security.
     *
     * @param length The length of the random string to generate
     * @return A random string of the specified length
     */
    public String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    /**
     * Generates a random string with a default length of 24 characters.
     * 
     * @return A random string of default length (24 characters)
     */
    public String generate() {
        return generate(24);
    }
}
