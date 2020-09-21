package com.lee.wechat.net.exception;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/10 21:28
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: Http异常状态码处理
 */
public class HttpStatusCodeException extends RuntimeException {

    /**
     * 请求错误
     */
    public static final int BAD_REQUEST = 400;

    /**
     * 认证失败
     */
    public static final int UNATHORIZED = 403;

    /**
     * 找不到资源
     */
    public static final int NOT_FOUND = 404;

    /**
     * 服务器错误
     */
    public static final int SERVICE_ERROR = 500;


    public static final String MSG_SERVICE_ERROR = "服务器错误";

    public static final String MSG_UNKNOWN_ERROR = "未知错误";

    public static final String MSG_NOT_FOUND = "找不到资源";

    public static final String MSG_UNATHORIZED = "认证失败";

    public static final String MSG_BAD_REQUEST = "请求错误";

    public HttpStatusCodeException(int code) {
        super(getMessage(code));
    }

    public static String getMessage(int code) {
        String msg;
        switch (code) {
            case BAD_REQUEST:
                msg = MSG_BAD_REQUEST;
                break;
            case UNATHORIZED:
                msg = MSG_UNATHORIZED;
                break;
            case NOT_FOUND:
                msg = MSG_NOT_FOUND;
                break;
            case SERVICE_ERROR:
                msg = MSG_SERVICE_ERROR;
                break;
            default:
                msg = MSG_UNKNOWN_ERROR;
                break;
        }
        return msg;
    }

}
