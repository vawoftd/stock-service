package com.vawo.foundation.stock.exception.advise;

import com.vawo.foundation.stock.exception.BusinessException;
import com.vawo.foundation.stock.utils.result.BaseResult;
import com.vawo.foundation.stock.utils.result.BaseResultUtils;
import com.vawo.foundation.stock.utils.result.CommonCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;

@RestControllerAdvice
public class ValidationExceptionAdvice extends BaseValidateExceptionAdvice {

    private Logger logger = LoggerFactory.getLogger(ValidationExceptionAdvice.class);

    @ExceptionHandler({ValidationException.class})
    @ResponseBody
    public BaseResult<String> errorHandler(ValidationException ex) {
        BusinessException be = null;

        Throwable throwable = ex.getCause();
        if (throwable instanceof BusinessException) {
            be = (BusinessException) throwable;
        } else if (throwable instanceof InvocationTargetException) {
            Throwable targetException = ((InvocationTargetException) throwable).getTargetException();
            if (targetException instanceof BusinessException) {
                be = ((BusinessException) targetException);
            }
        }

        if (be != null) {
            BaseResult<String> baseResult = BaseResultUtils.buildEmptyBaseResult();
            baseResult.setErrorCode(be.getCode().toString());
            baseResult.setErrorMsg(be.getMessage());
            return baseResult;
        } else {
            logger.error("参数验证发生异常", ex);
            BaseResult<String> baseResult = BaseResultUtils.buildBaseResult(CommonCodeEnum.PARAMETER_VALIDATE_FAILED);
            return baseResult;
        }
    }
}
