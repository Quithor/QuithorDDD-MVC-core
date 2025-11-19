package top.yanquithor.framework.dddbase.common.infrastructure.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Cron expression utility class for validating and processing cron expressions.
 * Provides methods to verify cron syntax and convert cron expressions to natural language descriptions.
 * Implements custom validation logic as well as delegates to Spring's built-in CronExpression class.
 *
 * @author YanQuithor
 * @since 2025-10-29
 */
@Slf4j
public final class CronUtil {
    
    private static final List<String> CRON_MONTH = List.of("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
    private static final List<String> CRON_DAY_OF_WEEK = List.of("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT");
    private final ChatClient client;
    
    public CronUtil(ChatClient client) {
        this.client = client;
    }
    
    /**
     * Verifies a cron expression by checking each component field against cron syntax rules.
     * Validates that the expression has 6 parts and each part follows cron syntax.
     *
     * @param cron Cron expression to validate
     * @return True if cron expression is valid, false otherwise
     */
    public boolean verify(String cron) {
        boolean hasSpaceChat = cron.contains(" ");
        if (!hasSpaceChat) return false;
        
        String[] parts = cron.split(" ");
        if (parts.length != 6) {
            log.info("Cron expression must have 6 parts"); // 记录表达式部分数错误
            return false;
        }
        return verifyCronSecondOrMinute(parts[0]) &&
                verifyCronSecondOrMinute(parts[1]) &&
                verifyCronHour(parts[2]) &&
                verifyCronDayOfMonth(parts[3]) &&
                verifyCronMonth(parts[4]) &&
                verifyCronDayOfWeek(parts[5]);
    }
    
    /**
     * Verifies a cron expression using Spring's built-in CronExpression class.
     * Provides an alternative validation method using Spring's implementation.
     *
     * @param cron Cron expression to validate
     * @return True if cron expression is valid according to Spring's implementation, false otherwise
     */
    public static boolean springInnerVerify(String cron) {
        if (cron == null || cron.trim().isEmpty()) return false;
        return CronExpression.isValidExpression(cron);
    }
    
    private boolean verifyCronSecondOrMinute(String secondOrMinute) {
        if (secondOrMinute.equals("*")) return true;
        if (secondOrMinute.matches("[0-9]+")) {
            int value = Integer.parseInt(secondOrMinute);
            return value >= 0 && value <= 59;
        }
        if (secondOrMinute.matches("[0-9]+-[0-9]+")) {
            String[] values = secondOrMinute.split("-");
            int start = Integer.parseInt(values[0]);
            int end = Integer.parseInt(values[1]);
            return (start >= 0 && start <= 59) &&
                    (end >= 0 && end <= 59) &&
                    start <= end;
        }
        if (secondOrMinute.matches("[0-9]+/[0-9]+")) {
            String[] values = secondOrMinute.split("/");
            int start = Integer.parseInt(values[0]);
            int step = Integer.parseInt(values[1]);
            return (start >= 0 && start <= 59) &&
                    (step >= 1 && step <= 59);
        }
        if (secondOrMinute.matches("/[0-9]+")) {
            int step = Integer.parseInt(secondOrMinute.replace("/", ""));
            return step >= 1 && step <= 59;
        }
        if (secondOrMinute.contains(",")) {
            return Arrays.stream(secondOrMinute.split(","))
                    .allMatch(this::verifyCronSecondOrMinute);
        }
        log.info("Cron expression must be a number between 0 and 59"); // 记录秒或分钟值范围错误
        return false;
    }
    
    private boolean verifyCronHour(String hour) {
        if (hour.equals("*")) return true;
        if (hour.matches("[0-9]+")) {
            int value = Integer.parseInt(hour);
            return value >= 0 && value <= 23;
        }
        if (hour.matches("[0-9]+-[0-9]+")) {
            String[] values = hour.split("-");
            int start = Integer.parseInt(values[0]);
            int end = Integer.parseInt(values[1]);
            return (start >= 0 && start <= 23) &&
                    (end >= 0 && end <= 23) &&
                    start <= end;
        }
        if (hour.matches("[0-9]+/[0-9]+")) {
            String[] values = hour.split("/");
            int start = Integer.parseInt(values[0]);
            int step = Integer.parseInt(values[1]);
            return (start >= 0 && start <= 23) &&
                    (step >= 1 && step <= 23);
        }
        if (hour.matches("/[0-9]+")) {
            int step = Integer.parseInt(hour.replace("/", ""));
            return step >= 1 && step <= 23;
        }
        if (hour.contains(",")) {
            return Arrays.stream(hour.split(","))
                    .allMatch(this::verifyCronHour);
        }
        log.info("Cron expression must be a number between 0 and 23"); // 记录小时值范围错误
        return false;
    }
    
    private boolean verifyCronDayOfMonth(String day) {
        if (day.equals("*")) return true;
        if (day.matches("[0-9]+")) {
            int value = Integer.parseInt(day);
            return value >= 1 && value <= 31;
        }
        if (day.matches("[0-9]+-[0-9]+")) {
            String[] values = day.split("-");
            int start = Integer.parseInt(values[0]);
            int end = Integer.parseInt(values[1]);
            return (start >= 1 && start <= 31) &&
                    (end >= 1 && end <= 31) &&
                    start <= end;
        }
        if (day.matches("[0-9]+/[0-9]+")) {
            String[] values = day.split("/");
            int start = Integer.parseInt(values[0]);
            int step = Integer.parseInt(values[1]);
            return (start >= 1 && start <= 31) &&
                    (step >= 1 && step <= 31);
        }
        if (day.matches("/[0-9]+")) {
            int step = Integer.parseInt(day.replace("/", ""));
            return step >= 1 && step <= 31;
        }
        if (day.contains(",")) {
            return Arrays.stream(day.split(","))
                    .allMatch(this::verifyCronDayOfMonth);
        }
        log.info("Cron expression must be a number between 1 and 31"); // 记录日期值范围错误
        return false;
    }
    
    private boolean verifyCronMonth(String month) {
        if (month.equals("*") || CRON_MONTH.contains(month.toUpperCase())) return true;
        if (month.matches("[0-9]+")) {
            int value = Integer.parseInt(month);
            return value >= 1 && value <= 12;
        }
        if (month.matches("[0-9]+-[0-9]+")) {
            String[] values = month.split("-");
            int start = Integer.parseInt(values[0]);
            int end = Integer.parseInt(values[1]);
            return (start >= 1 && start <= 12) &&
                    (end >= 1 && end <= 12) &&
                    start <= end;
        }
        if (month.matches("[0-9]+/[0-9]+")) {
            String[] values = month.split("/");
            int start = Integer.parseInt(values[0]);
            int step = Integer.parseInt(values[1]);
            return (start >= 1 && start <= 12) &&
                    (step >= 1 && step <= 12);
        }
        if (month.matches("[a-zA-Z]{3}-[a-zA-Z]{3}")) {
            String[] values = month.split("-");
            return  CRON_MONTH.contains(values[0].toUpperCase()) &&
                    CRON_MONTH.contains(values[1].toUpperCase()) &&
                    values[0].compareTo(values[1]) <= 0;
        }
        if (month.matches("[a-zA-Z]{3}/[a-zA-Z]{3}")) {
            String[] values = month.split("/");
            return CRON_MONTH.contains(values[0]) && CRON_MONTH.contains(values[1]);
        }
        if (month.matches("/[0-9]+")) {
            int step = Integer.parseInt(month.replace("/", ""));
            return step >= 1 && step <= 12;
        }
        if (month.matches("/[a-zA-Z]{3}")) {
            return CRON_MONTH.contains(month.replace("/", "").toUpperCase());
        }
        if (month.contains(",")) {
            return Arrays.stream(month.split(","))
                    .allMatch(this::verifyCronMonth);
        }
        log.info("Cron expression must be a number between 1 and 12"); // 记录月份值范围错误
        return false;
    }
    
    private boolean verifyCronDayOfWeek(String day) {
        if (day.equals("*") || day.equals("?") || CRON_DAY_OF_WEEK.contains(day.toUpperCase())) return true;
        if (day.matches("[0-9]+")) {
            int value = Integer.parseInt(day);
            return value >= 0 && value <= 7;
        }
        if (day.matches("[0-9]+-[0-9]+") || day.matches("[a-zA-Z]{3}-[a-zA-Z]{3}")) {
            String[] values = day.split("-");
            if (values[0].matches("[0-9]+")) {
                int start = Integer.parseInt(values[0]);
                int end = Integer.parseInt(values[1]);
                return (start >= 0 && start <= 7) &&
                        (end >= 0 && end <= 7) &&
                        start <= end;
            } else {
                return CRON_DAY_OF_WEEK.contains(values[0].toUpperCase()) &&
                        CRON_DAY_OF_WEEK.contains(values[1].toUpperCase()) &&
                        values[0].compareTo(values[1]) <= 0;
            }
        }
        if (day.matches("[0-9]+/[0-9]+") || day.matches("[a-zA-Z]{3}/[a-zA-Z]{3}")) {
            String[] values = day.split("/");
            if (values[0].matches("[0-9]+")) {
                int start = Integer.parseInt(values[0]);
                int step = Integer.parseInt(values[1]);
                return (start >= 0 && start <= 7) &&
                        (step >= 1 && step <= 7);
            } else {
                return CRON_DAY_OF_WEEK.contains(values[0].toUpperCase()) &&
                        CRON_DAY_OF_WEEK.contains(values[1].toUpperCase());
            }
        }
        if (day.matches("/[0-9]+")) {
            int step = Integer.parseInt(day.replace("/", ""));
            return step >= 1 && step <= 7;
        }
        if (day.matches("/[a-zA-Z]{3}")) {
            return CRON_DAY_OF_WEEK.contains(day.replace("/", "").toUpperCase());
        }
        if (day.contains(",")) {
            return Arrays.stream(day.split(","))
                    .allMatch(this::verifyCronDayOfWeek);
        }
        log.info("Cron expression must be a number between 0 and 7"); // 记录星期值范围错误
        return false;
    }
    
    /**
     * Converts a cron expression to natural language description using AI.
     * Uses Spring AI to generate human-readable descriptions of cron expressions.
     *
     * @param cron Cron expression to convert
     * @param language Target language for the description
     * @return Natural language description of the cron expression
     */
    public String preseasonToNatureLanguage(String cron, String language) {
        PromptTemplate userPrompt = new PromptTemplate("""
                现在有一个Cron表达式 {cron} （Spring支持的语法版本）
                用户和想要知道这个表达式的含义，尽量使用通俗易懂的语言解析出cron的含义
                仅输出表达式所表述的执行内容，不需要额外输出""");
        userPrompt.add("cron", cron);
        PromptTemplate systemPrompt = new PromptTemplate("""
                用户想要的输出语言为： {language}""");
        systemPrompt.add("language", language);
        return client.prompt(userPrompt.create())
                .system(systemPrompt.create().getContents())
                .call()
                .content();
    }
}
