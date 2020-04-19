package com.vawo.foundation.demo.exception.advise;

import com.vawo.foundation.demo.utils.result.BaseResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created with IntelliJ IDEA.
 * User: pengyujia
 * Date: 2018/10/18
 * Time: 下午11:29
 * To change this template use File | Settings | File Templates.
 */
@RestControllerAdvice
public class ValidateBindExceptionAdvice extends BaseValidateExceptionAdvice {

    @ExceptionHandler({BindException.class})
    @ResponseBody
    public BaseResult<String> errorHandler(BindException ex) {
        return this.processCommonMessage(ex.getBindingResult());
    }
}
