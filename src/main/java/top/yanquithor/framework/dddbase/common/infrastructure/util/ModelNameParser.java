package top.yanquithor.framework.dddbase.common.infrastructure.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Model name parsing utility class that extracts vendor, display name, and model size from request names.
 * Parses formats like vendor/model-name-size and returns the individual components.
 * Useful for processing AI model identifiers in DDD-based applications.
 *
 * @author YanQuithor
 * @since 2025-10-29
 */
@Slf4j
public class ModelNameParser {
    
    /**
     * Parses a request name to extract model information components.
     * For example:
     * "deepseek-ai/DeepSeek-R1-Distill-Qwen-7B" -> vendor: "deepseek-ai", display name: "DeepSeek-R1-Distill-Qwen", model size: "7B"
     * "Qwen/Qwen2.5-Coder-7B-Instruct" -> vendor: "Qwen", display name: "Qwen2.5-Coder", model size: "7B"
     * 
     * @param requestName The request name to parse (format: vendor/model-size)
     * @return Array containing [vendor, displayName, modelSize]
     */
    public String[] parseModelInfo(String requestName) {
        String vendor = "";
        String displayName = "";
        String modelSize = "";
        
        if (requestName != null && requestName.contains("/")) {
            String[] parts = requestName.split("/");
            if (parts.length >= 2) {
                vendor = parts[0];
                String modelPart = parts[1];
                
                // 从request_name中提取模型体量和显示名称 - 从请求名称中提取模型体量和显示名称
                if (modelPart.contains("-")) {
                    String[] modelParts = modelPart.split("-");
                    if (modelParts.length >= 2) {
                        // 查找包含数字的部分作为模型体量 - 查找包含数字的部分作为模型体量
                        int sizeIndex = -1;
                        
                        // 从后向前查找第一个包含数字的部分 - 从后向前查找第一个包含数字的部分
                        for (int i = modelParts.length - 1; i >= 0; i--) {
                            if (modelParts[i].matches(".*\\d+.*")) {  // 包含数字 - 包含数字
                                sizeIndex = i;
                                modelSize = modelParts[i];
                                break;
                            }
                        }
                        
                        // 如果没找到包含数字的部分，使用最后一个部分作为体量 - 如果没找到包含数字的部分，使用最后一个部分作为体量
                        if (sizeIndex == -1) {
                            sizeIndex = modelParts.length - 1;
                            modelSize = modelParts[sizeIndex];
                        }
                        
                        // 构建显示名称：连接体量之前的所有部分 - 构建显示名称：连接体量之前的所有部分
                        StringBuilder nameBuilder = new StringBuilder();
                        for (int i = 0; i < sizeIndex; i++) {
                            if (!nameBuilder.isEmpty()) {
                                nameBuilder.append("-");
                            }
                            nameBuilder.append(modelParts[i]);
                        }
                        displayName = nameBuilder.toString();
                    } else {
                        displayName = modelPart;
                    }
                } else {
                    displayName = modelPart;
                }
            }
        }
        
        return new String[]{vendor, displayName, modelSize};
    }
    
    /**
     * Extracts the vendor name from a request name.
     * 
     * @param requestName The request name to extract vendor from
     * @return The vendor name, or empty string if not found
     */
    public String extractVendor(String requestName) {
        String[] parts = parseModelInfo(requestName);
        return parts[0];
    }
    
    /**
     * Extracts the display name from a request name.
     * 
     * @param requestName The request name to extract display name from
     * @return The display name, or empty string if not found
     */
    public String extractDisplayName(String requestName) {
        String[] parts = parseModelInfo(requestName);
        return parts[1];
    }
    
    /**
     * Extracts the model size from a request name.
     * 
     * @param requestName The request name to extract model size from
     * @return The model size, or empty string if not found
     */
    public String extractModelSize(String requestName) {
        String[] parts = parseModelInfo(requestName);
        return parts[2];
    }
}