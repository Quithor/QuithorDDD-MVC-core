package top.yanquithor.framework.dddbase.common.application.command;

/**
 * Result Generic Class - Used for unified response format
 *
 * @author YanQuithor
 * @version 1.1.1
 * @since 2025-12-13
 */
public record Result<T>(
        int code,
        T data,
        String msg
) {

    // 成功无数据的响应
    public static  <T> Result<T> success() {
        return new Result<>(0, null, "success");
    }

    // 成功有数据的响应
    public static  <T> Result<T> successWithData(T data) {
        return new Result<>(0, data, "success");
    }

    // 成功带自定义消息的响应
    public static  <T> Result<T> successWithMessage(String msg) {
        return new Result<>(0, null, msg);
    }

    // 成功带数据和自定义消息的响应
    public static  <T> Result<T> successWithDataAndMessage(T data, String msg) {
        return new Result<>(0, data, msg);
    }

    // 错误响应
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, null, msg);
    }
}
