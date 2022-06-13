package com.lx.reggie.handle;

import com.lx.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author 小兴
 * @description TODO 全局异常处理器
 * @className GlobalExceptionHandle
 * @date 2022/6/11 13:26
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandle {

    /**
     * 异常处理方法
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R exceptionHandle(SQLIntegrityConstraintViolationException e){
        String message = e.getMessage();
        log.error(message);

        if (message.contains("Duplicate entry")){
            // 唯一的值冲突错误
            String[] split = message.split(" ");
            String name = split[2].replaceAll("'", "");
            return R.error(name + "已经存在了");
        }
        return R.error("失败了");
    }
}
