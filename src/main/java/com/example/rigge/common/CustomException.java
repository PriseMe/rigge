package com.example.rigge.common;

/**
 * @version v1.0
 * @ProjectName: rigge
 * @ClassName: customException
 * @Description: TODO(一句话描述该类的功能)
 * @Author:Promiseme
 * @Date: 2022/9/3 22:32
 */
public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
