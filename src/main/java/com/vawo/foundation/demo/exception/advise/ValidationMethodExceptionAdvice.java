package com.vawo.foundation.demo.exception.advise;

import com.vawo.foundation.demo.utils.I18nMessageUtils;
import com.vawo.foundation.demo.utils.JSONUtil;
import com.vawo.foundation.demo.utils.result.BaseResult;
import com.vawo.foundation.demo.utils.result.BaseResultUtils;
import com.vawo.foundation.demo.utils.result.CommonCodeEnum;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ResourceBundle;
import java.util.Set;

@RestControllerAdvice
public class ValidationMethodExceptionAdvice {

    @NotEmpty
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public BaseResult<String> errorHandler(ConstraintViolationException ex) {
        BaseResult<String> baseResult = BaseResultUtils.buildBaseResult(CommonCodeEnum.PARAMETER_VALIDATE_FAILED);
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        StringBuilder errorMessageBuilder = new StringBuilder();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            for (ConstraintViolation constraintViolation : constraintViolations) {
                String defaultMessage = constraintViolation.getMessage();
                //带有error code的message 里面尝试转换成，通用格式
                if ((!StringUtils.isEmpty(defaultMessage)) && defaultMessage.contains("errorCode")) {
                    try {
                        baseResult = JSONUtil.parseObject(defaultMessage, BaseResult.class);
                        if (baseResult != null) {
                            String i18nMessage = I18nMessageUtils.getValidateMessage(baseResult.getErrorMsg());
                            if (!StringUtils.isEmpty(i18nMessage)) {
                                baseResult.setErrorMsg(i18nMessage);
                            }
                            return baseResult;
                        }
                    } catch (Exception e) {
                    }
                }
                errorMessageBuilder.append(defaultMessage + "|");
            }
        }
        baseResult.setErrorMsg(errorMessageBuilder.substring(0, errorMessageBuilder.indexOf("|")));
        return baseResult;
    }

}



