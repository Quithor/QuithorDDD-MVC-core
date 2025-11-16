package top.yanquithor.framework.dddbase.common.infrastructure.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * CSV utility class that provides CSV file reading and object mapping functionality.
 */
@Slf4j
@Component
public class CsvUtils {
    
    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    /**
     * Reads a CSV file and maps it to a list of objects of the specified type
     * @param clazz Target object type
     * @param csvFile CSV file
     * @param <E> Object type
     * @return List of objects
     */
    public <E> List<E> readCsv(Class<E> clazz, File csvFile) {
        List<E> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8))) {
            String csvHeader = reader.readLine();
            if (csvHeader == null || csvHeader.trim().isEmpty()) {
                log.warn("CSV file is empty or has no header information");
                return result;
            }
            
            log.debug("CSV Header: \"{}\"", csvHeader);
            String[] headers = parseLine(csvHeader);
            Map<String, Integer> headerIndexMap = createHeaderIndexMap(headers);
            
            // 获取类的字段映射
            Map<String, Field> fieldMap = createFieldMap(clazz, headers);
            
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                log.debug("CSV Line {}: \"{}\"", ++lineNum, line);
                String[] values = parseLine(line);
                
                // 创建对象实例并设置字段值
                E instance = createInstance(clazz, headers, values, fieldMap, headerIndexMap);
                if (instance != null) {
                    result.add(instance);
                }
            }
        } catch (IOException e) {
            log.error("Error occurred while reading CSV file", e);
        } catch (Exception e) {
            log.error("Error occurred while processing CSV file", e);
        }
        return result;
    }
    
    /**
     * Parses a CSV line, correctly handling quotes and escapes
     *
     * @param line CSV line data
     * @return Parsed field array
     */
    private String[] parseLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean insideQuotes = false;
        int i = 0;
        
        while (i < line.length()) {
            char c = line.charAt(i);
            
            if (c == CsvUtils.DEFAULT_QUOTE) {
                // 检查是否是转义的引号（连续两个引号）
                if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == CsvUtils.DEFAULT_QUOTE) {
                    currentField.append(CsvUtils.DEFAULT_QUOTE);
                    i += 2; // 跳过两个引号
                } else {
                    // 切换引号状态
                    insideQuotes = !insideQuotes;
                    i++;
                }
            } else if (c == CsvUtils.DEFAULT_SEPARATOR && !insideQuotes) {
                // 遇到分隔符且不在引号内，则为字段结束
                fields.add(currentField.toString());
                currentField.setLength(0); // 清空StringBuilder
                i++;
            } else {
                currentField.append(c);
                i++;
            }
        }
        
        // 添加最后一个字段
        fields.add(currentField.toString());
        
        return fields.toArray(new String[0]);
    }
    
    /**
     * Creates a mapping from header fields to their indices
     * @param headers Header field array
     * @return Mapping from header fields to indices
     */
    private Map<String, Integer> createHeaderIndexMap(String[] headers) {
        Map<String, Integer> headerIndexMap = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            headerIndexMap.put(headers[i].trim(), i);
        }
        return headerIndexMap;
    }
    
    /**
     * Creates a mapping from class fields to Field objects
     * @param clazz Type
     * @param headers CSV header fields
     * @return Mapping from field names to Field objects
     */
    private Map<String, Field> createFieldMap(Class<?> clazz, String[] headers) {
        Map<String, Field> fieldMap = new HashMap<>();
        
        // 获取所有声明的字段（包括私有字段）
        Field[] declaredFields = clazz.getDeclaredFields();
        Set<String> headerSet = new HashSet<>();
        for (String header : headers) {
            headerSet.add(header.trim());
        }
        
        for (Field field : declaredFields) {
            // 检查字段名是否在CSV头部中存在
            if (headerSet.contains(field.getName())) {
                field.setAccessible(true); // 允许访问私有字段
                fieldMap.put(field.getName(), field);
            }
        }
        
        return fieldMap;
    }
    
    /**
     * Creates a class instance and sets field values
     * @param clazz Type
     * @param headers Header fields
     * @param values Field values
     * @param fieldMap Field mapping
     * @param headerIndexMap Header index mapping
     * @param <E> Object type
     * @return Created object instance
     */
    private <E> E createInstance(Class<E> clazz, String[] headers, String[] values, 
                                 Map<String, Field> fieldMap, Map<String, Integer> headerIndexMap) {
        try {
            E instance = clazz.getDeclaredConstructor().newInstance();
            
            for (String header : headers) {
                String trimmedHeader = header.trim();
                Integer index = headerIndexMap.get(trimmedHeader);
                
                if (index != null && index < values.length) {
                    Field field = fieldMap.get(trimmedHeader);
                    if (field != null) {
                        String value = values[index];
                        setFieldValue(instance, field, value);
                    }
                }
            }
            
            return instance;
        } catch (Exception e) {
            log.error("Error creating instance of class: {}", clazz.getName(), e);
            return null;
        }
    }
    
    /**
     * Sets object field value
     * @param instance Object instance
     * @param field Field
     * @param value Value string
     * @param <E> Object type
     */
    private <E> void setFieldValue(E instance, Field field, String value) {
        try {
            Class<?> fieldType = field.getType();
            
            // 尝试将字符串值转换为对应类型
            Object convertedValue = convertValue(value, fieldType);
            
            field.set(instance, convertedValue);
        } catch (IllegalAccessException e) {
            log.error("Cannot set field value for field: {}", field.getName(), e);
        } catch (Exception e) {
            log.error("Error converting value '{}' for field: {}", value, field.getName(), e);
        }
    }
    
    /**
     * Converts a string value to the specified type
     * @param value String value
     * @param targetType Target type
     * @return Converted value
     */
    private Object convertValue(String value, Class<?> targetType) {
        if (value == null || value.trim().isEmpty()) {
            // 如果值为空，根据类型返回默认值
            if (targetType.isPrimitive()) {
                if (targetType == int.class || targetType == Integer.class) return 0;
                if (targetType == long.class || targetType == Long.class) return 0L;
                if (targetType == double.class || targetType == Double.class) return 0.0;
                if (targetType == float.class || targetType == Float.class) return 0.0f;
                if (targetType == boolean.class || targetType == Boolean.class) return false;
                if (targetType == byte.class || targetType == Byte.class) return (byte) 0;
                if (targetType == char.class || targetType == Character.class) return '\0';
                if (targetType == short.class || targetType == Short.class) return (short) 0;
            }
            return null;
        }
        
        value = value.trim();
        
        if (targetType == String.class) {
            return value;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value);
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.parseFloat(value);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (targetType == byte.class || targetType == Byte.class) {
            return Byte.parseByte(value);
        } else if (targetType == char.class || targetType == Character.class) {
            return !value.isEmpty() ? value.charAt(0) : '\0';
        } else if (targetType == short.class || targetType == Short.class) {
            return Short.parseShort(value);
        } else {
            // 对于其他类型，返回原始字符串值
            log.warn("Unsupported type: {}, returning string value: {}", targetType, value);
            return value;
        }
    }
    
    /**
     * Reads CSV and returns raw string data (without object mapping)
     * @param csvFile CSV file
     * @return Two-dimensional list, outer list represents rows, inner list represents fields in each row
     */
    public List<List<String>> readRawCsv(File csvFile) {
        List<List<String>> result = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = parseLine(line);
                List<String> row = Arrays.asList(values);
                result.add(row);
            }
        } catch (IOException e) {
            log.error("Error occurred while reading CSV file", e);
        }
        
        return result;
    }
    
    /**
     * Writes a list of objects to a CSV file
     * @param data List of objects
     * @param clazz Object type
     * @param outputFile Output file
     * @param <E> Object type
     */
    public <E> void writeCsv(List<E> data, Class<E> clazz, File outputFile) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8))) {
            if (data == null || data.isEmpty()) {
                log.warn("Attempting to write empty data to CSV file: {}", outputFile.getAbsolutePath());
                return;
            }

            // 获取类的字段并写入头部
            Field[] fields = clazz.getDeclaredFields();
            StringBuilder headerLine = new StringBuilder();
            for (int i = 0; i < fields.length; i++) {
                if (i > 0) headerLine.append(DEFAULT_SEPARATOR);
                headerLine.append(escapeField(fields[i].getName()));
            }
            writer.write(headerLine.toString());
            writer.newLine();

            // 写入数据行
            for (E item : data) {
                StringBuilder rowLine = new StringBuilder();
                for (int i = 0; i < fields.length; i++) {
                    if (i > 0) rowLine.append(DEFAULT_SEPARATOR);
                    Field field = fields[i];
                    field.setAccessible(true); // 允许访问私有字段
                    try {
                        Object value = field.get(item);
                        rowLine.append(escapeField(value != null ? value.toString() : ""));
                    } catch (IllegalAccessException e) {
                        log.error("Cannot access field: {}", field.getName(), e);
                        rowLine.append(" "); // 字段访问失败时写入空值
                    }
                }
                writer.write(rowLine.toString());
                writer.newLine();
            }
            
            log.info("Successfully wrote {} records to CSV file: {}", data.size(), outputFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("Error occurred while writing CSV file: " + outputFile.getAbsolutePath(), e);
        }
    }
    
    /**
     * Writes a list of string lists to a CSV file
     * @param data Two-dimensional string list, outer list represents rows, inner list represents fields in each row
     * @param outputFile Output file
     */
    public void writeCsv(List<List<String>> data, File outputFile) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8))) {
            if (data == null) {
                log.warn("Attempting to write empty data to CSV file: {}", outputFile.getAbsolutePath());
                return;
            }

            for (int i = 0; i < data.size(); i++) {
                List<String> row = data.get(i);
                StringBuilder rowLine = new StringBuilder();
                for (int j = 0; j < row.size(); j++) {
                    if (j > 0) rowLine.append(DEFAULT_SEPARATOR);
                    rowLine.append(escapeField(row.get(j)));
                }
                writer.write(rowLine.toString());
                if (i < data.size() - 1) { // 不在最后一行添加换行符
                    writer.newLine();
                }
            }
            
            log.info("Successfully wrote {} rows of data to CSV file: {}", data.size(), outputFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("Error occurred while writing CSV file: {}", outputFile.getAbsolutePath(), e);
        }
    }
    
    /**
     * Escapes CSV field, handling fields containing special characters
     * @param field Field value
     * @return Escaped field value
     */
    private String escapeField(String field) {
        if (field == null) {
            return "";
        }
        
        // 如果字段包含分隔符、引号或换行符，则需要用引号包围，并转义其中的引号
        if (field.contains(String.valueOf(DEFAULT_SEPARATOR)) || 
            field.contains(String.valueOf(DEFAULT_QUOTE)) || 
            field.contains("\n") || 
            field.contains("\r")) {
            
            // 将引号转义为两个引号
            return DEFAULT_QUOTE + field.replace(String.valueOf(DEFAULT_QUOTE), 
                                                 String.valueOf(DEFAULT_QUOTE) + DEFAULT_QUOTE) + DEFAULT_QUOTE;
        }
        
        return field;
    }
}