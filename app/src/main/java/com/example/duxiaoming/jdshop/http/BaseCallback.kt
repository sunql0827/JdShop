package com.example.duxiaoming.jdshop.http

import android.content.Context
import com.google.gson.internal.`$Gson$Types`
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response

import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


abstract class BaseCallback<in T> constructor( var context:Context){


    var mType: Type

    init {
        mType = getSuperclassTypeParameter(javaClass)
    }


    abstract fun onBeforeRequest(request: Request)


    abstract fun onFailure(request: Request, e: Exception)


    /**
     * 请求成功时调用此方法
     * @param response
     */
    abstract fun onResponse(response: Response)

    /**

     * 状态码大于200，小于300 时调用此方法
     * @param response
     * *
     * @param t
     * *
     * @throws IOException
     */
    abstract fun onSuccess(response: Response, t: T)

    /**
     * 状态码400，404，403，500等时调用此方法
     * @param response
     * *
     * @param code
     * *
     * @param e
     */
    abstract fun onError(response: Response, code: Int, e: Exception)


    /**
     * Token 验证失败。状态码401,402,403 等时调用此方法
     * @param response
     * *
     * @param code
     */
    abstract fun onTokenError(response: Response, code: Int)

    companion object {

        internal fun getSuperclassTypeParameter(subclass: Class<*>): Type {
            val superclass = subclass.genericSuperclass
            if (superclass is Class<*>) {
                throw RuntimeException("Missing type parameter.")
            }
            val parameterized = superclass as ParameterizedType
            return `$Gson$Types`.canonicalize(parameterized.actualTypeArguments[0])
        }
    }

}