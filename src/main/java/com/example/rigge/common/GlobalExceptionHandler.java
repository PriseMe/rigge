package com.example.rigge.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @version v1.0
 * @ProjectName: rigge
 * @ClassName: GlobalExceptionHandler
 * @Description: TODO(一句话描述该类的功能)
 * @Author:Promiseme
 * @Date: 2022/9/3 0:07
 */
/**
 * 全局异常处理
 */
@ControllerAdvice(annotations = {Controller.class, RestController.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException sqlex){
        log.info("错误信息:{}" , sqlex.getMessage());

        if (sqlex.getMessage().contains("Duplicate entry")){
            String[] err = sqlex.getMessage().split(" ");
            String errMessage = err[2] + "已存在";
            return R.error(errMessage);
        }
        return R.error("未知错误");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex){
        log.info("exceptionHandler......{}",ex.getMessage());
        return R.error(ex.getMessage());
    }

}
