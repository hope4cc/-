package com.paopao.exception.handler;

import com.paopao.exception.GlobalException;
import com.paopao.model.modelView.BaseModelView;
import com.paopao.model.modelView.GeneralResponse;
import com.paopao.util.MyHttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 全局异常处理（注意只捕捉非运行时异常）
 */
@ControllerAdvice
public class ExceptionHandler {

    /**
     * 所有controller层抛出的IonaException异常都会再此统一处理
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(GlobalException.class)
    @ResponseBody
    public BaseModelView errorHandler(Exception e) {
        return new BaseModelView(MyHttpStatus.IONA_ERROR,e.getMessage());
    }

    /**
     * 所有controller层抛出的IOException异常都会再此统一处理
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
    @ResponseBody
    public GeneralResponse errorHandlerIO(Exception e) {
        e.printStackTrace();
        return new GeneralResponse(MyHttpStatus.IO_ERROR);
    }
}
