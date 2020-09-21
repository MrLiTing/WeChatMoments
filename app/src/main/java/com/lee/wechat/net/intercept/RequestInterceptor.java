package com.lee.wechat.net.intercept;


import com.lee.wechat.net.exception.HttpStatusCodeException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @Author: Lee
 * @CreateDate: 2020/9/10 19:42
 * @company:LeeStudio
 * @email: ltms2013@outlook.com
 * @Description: 请求拦截器
 */
public class RequestInterceptor implements Interceptor {


    /**
     * 拦截方法，添加对错误Http状态码处理
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();

        Response response = chain.proceed(request.build());
        //异常状态码，抛出异常
        if (response.code() >= HttpStatusCodeException.BAD_REQUEST) {
            throw new HttpStatusCodeException(response.code());
        }
        return response;
    }

}
