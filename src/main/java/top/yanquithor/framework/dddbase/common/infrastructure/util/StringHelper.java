package top.yanquithor.framework.dddbase.common.infrastructure.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * String utility class providing common string operations for DDD-based applications.
 * Includes validation, transformation, and formatting methods for string manipulation.
 *
 * @author YanQuithor
 * @since 2025-10-29
 */
public class StringHelper {
    
    /**
     * Checks if a string is null or blank (empty or only whitespace).
     * A string is considered blank if it is null or contains only whitespace characters.
     *
     * @param str The string to check for null or blank status
     * @return true if null or blank, false otherwise
     */
    public boolean isBlank(String str) {
        return str == null || str.trim().isEmpty(); // 检查字符串是否为null或空白
    }
    
    /**
     * Checks if a string is not null and not blank.
     * Convenience method that negates the result of isBlank.
     *
     * @param str The string to check
     * @return true if not null and not blank, false otherwise
     */
    public boolean isNotBlank(String str) {
        return !isBlank(str);
    }
    
    /**
     * Converts a string to camelCase format.
     * Handles spaces, underscores, and hyphens as word separators.
     * First word is lowercased, subsequent words are capitalized.
     *
     * @param str The string to convert to camelCase
     * @return camelCase string, or original string if input is blank
     */
    public String toCamelCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        
        String[] parts = str.split("[\\s_-]+"); // 拆分字符串
        StringBuilder camelCaseStr = new StringBuilder(parts[0].toLowerCase());
        
        for (int i = 1; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                camelCaseStr.append(parts[i].substring(0, 1).toUpperCase())
                        .append(parts[i].substring(1).toLowerCase());
            }
        }
        
        return camelCaseStr.toString();
    }
    
    /**
     * Converts a string to PascalCase (UpperCamelCase) format.
     * First character of the converted string is capitalized.
     *
     * @param str The string to convert to PascalCase
     * @return PascalCase string, or original string if input is blank
     */
    public String toPascalCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        
        String camelCase = toCamelCase(str);
        if (camelCase.isEmpty()) {
            return camelCase;
        }
        
        return camelCase.substring(0, 1).toUpperCase() + camelCase.substring(1);
    }
    
    /**
     * Splits a string by delimiter and returns a list of non-empty parts.
     * Trims whitespace from each part and filters out empty strings.
     *
     * @param str       The string to split
     * @param delimiter The delimiter to use for splitting
     * @return List of string parts, or empty list if input is blank
     */
    public List<String> splitToList(String str, String delimiter) {
        if (isBlank(str)) {
            return List.of();
        }
        
        return Arrays.stream(str.split(delimiter))
                .map(String::trim) // 去除每个部分的空白字符
                .filter(part -> !part.isEmpty()) // 过滤空字符串
                .collect(Collectors.toList());
    }
    
    /**
     * Joins a list of strings with a specified delimiter.
     * Handles null or empty lists by returning an empty string.
     *
     * @param list      The list of strings to join
     * @param delimiter The delimiter to use between elements
     * @return Joined string, or empty string if list is null or empty
     */
    public String join(List<String> list, String delimiter) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        
        return String.join(delimiter, list);
    }
    
    /**
     * Truncates a string to specified length and adds ellipsis if needed.
     * If ellipsis is too long for remaining space, returns only ellipsis.
     *
     * @param str      The string to truncate
     * @param len      The maximum length of result string including ellipsis
     * @param ellipsis The ellipsis to append (e.g., "...")
     * @return Truncated string with ellipsis, or original string if not longer than len
     */
    public String truncate(String str, int len, String ellipsis) {
        if (str == null || str.length() <= len) {
            return str;
        }
        
        if (ellipsis == null) {
            ellipsis = "...";
        }
        
        int truncateLen = len - ellipsis.length();
        if (truncateLen < 0) {
            return ellipsis.substring(0, Math.min(ellipsis.length(), len)); // 修正过长的省略号
        }
        
        return str.substring(0, truncateLen) + ellipsis;
    }
    
    /**
     * Truncates a string to specified length with default ellipsis ("...").
     * Convenience method using default ellipsis.
     *
     * @param str The string to truncate
     * @param len The maximum length of result string including ellipsis
     * @return Truncated string with "..." ellipsis
     */
    public String truncate(String str, int len) {
        return truncate(str, len, "...");
    }
    
    /**
     * Removes all occurrences of a substring from a string.
     * Returns original string if either input string or remove string is blank.
     *
     * @param str    The original string
     * @param remove The substring to remove
     * @return String with substring removed, or original string if inputs are blank
     */
    public String remove(String str, String remove) {
        if (isBlank(str) || isBlank(remove)) {
            return str;
        }
        
        return str.replace(remove, "");
    }
}