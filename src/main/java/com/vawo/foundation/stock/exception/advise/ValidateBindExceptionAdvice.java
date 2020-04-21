package com.vawo.foundation.stock.exception.advise;

import com.vawo.foundation.stock.utils.result.BaseResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidateBindExceptionAdvice extends BaseValidateExceptionAdvice {

    @ExceptionHandler({BindException.class})
    @ResponseBody
    public BaseResult<String> errorHandler(BindException ex) {
        return this.processCommonMessage(ex.getBindingResult());
    }
}
