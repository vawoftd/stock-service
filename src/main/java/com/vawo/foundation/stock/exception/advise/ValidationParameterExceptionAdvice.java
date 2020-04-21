package com.vawo.foundation.stock.exception.advise;

import com.vawo.foundation.stock.utils.result.BaseResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationParameterExceptionAdvice extends BaseValidateExceptionAdvice{

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public BaseResult<String> errorHandler(MethodArgumentNotValidException ex) {
        return this.processCommonMessage(ex.getBindingResult());
    }


}
