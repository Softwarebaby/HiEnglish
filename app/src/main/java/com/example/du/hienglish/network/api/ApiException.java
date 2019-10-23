package com.example.du.hienglish.network.api;

/**
 * Created by Bob Du on 2018/8/9 10:41
 */
public class ApiException extends RuntimeException {
    //定义返回的结果码
    public static final int SERVER_ERROR = 500;
    public static final int LOGIN_ERROR = 511;
    public static final int REGISTER_ERROR = 512;
    public static final int NO_DATA = 513;

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String resultMessage) {
        super(resultMessage);
    }

    /**
     * 将错误码转换成错误信息
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case SERVER_ERROR:
                message = "服务端错误！";
                break;
            case LOGIN_ERROR:
                message = "登录失败，请检查手机号和密码！";
                break;
            case REGISTER_ERROR:
                message = "注册失败！";
                break;
            case NO_DATA:
                message = "暂无数据！";
                break;
            default:
                message = "未知错误！";
                break;
        }
        return message;
    }
}
