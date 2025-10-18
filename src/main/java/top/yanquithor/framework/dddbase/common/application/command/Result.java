package top.yanquithor.framework.dddbase.common.application.command;

public record Result<T>(
        int code,
        T data,
        String msg
) {
    
    public static  <T> Result<T> success() {
        return new Result<>(0, null, "success");
    }
    
    public static  <T> Result<T> successWithData(T data) {
        return new Result<>(0, data, "success");
    }
    
    public static  <T> Result<T> successWithMessage(String msg) {
        return new Result<>(0, null, msg);
    }
    
    public static  <T> Result<T> successWithDataAndMessage(T data, String msg) {
        return new Result<>(0, data, msg);
    }
    
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, null, msg);
    }
}
