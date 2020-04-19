package com.vawo.foundation.demo.exception.advise;

import com.vawo.foundation.demo.utils.I18nMessageUtils;
import com.vawo.foundation.demo.utils.JSONUtil;
import com.vawo.foundation.demo.utils.result.BaseResult;
import com.vawo.foundation.demo.utils.result.BaseResultUtils;
import com.vawo.foundation.demo.utils.result.CommonCodeEnum;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: pengyujia
 * Date: 2019/3/11
 * Time: 下午11:29
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseValidateExceptionAdvice {



    protected BaseResult processCommonMessage(BindingResult bindingResult){
        BaseResult<String> baseResult = BaseResultUtils.buildBaseResult(CommonCodeEnum.PARAMETER_VALIDATE_FAILED);
        StringBuilder errorMessageBuilder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String defaultMessage = fieldError.getDefaultMessage();
            if ((!StringUtils.isEmpty(defaultMessage)) && fieldError.getDefaultMessage().contains("errorCode")) {
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
            errorMessageBuilder.append(fieldError.getField());
            errorMessageBuilder.append(" ");
            errorMessageBuilder.append(fieldError.getDefaultMessage());
            errorMessageBuilder.append("|");
        }

        if (errorMessageBuilder.indexOf("|")>0)
            Objects.requireNonNull(baseResult).setErrorMsg(errorMessageBuilder.substring(0, errorMessageBuilder.indexOf("|")));
        else{//代表这是我自定义的校验注解未通过,需要从errors里拿msg
            String str = bindingResult.toString();
            int index  = str.lastIndexOf("default message");
            str = str.substring(index+17,str.length()-1);
            //这里的str的格式就是 msg|code的格式了 所以要拆分出msg和code
            String msg  = str.substring(0,str.lastIndexOf("|")-1);
            String code = str.substring(str.lastIndexOf("|")+1);
            Objects.requireNonNull(baseResult).setErrorMsg(msg);
            Objects.requireNonNull(baseResult).setErrorCode(code);
        }
        return baseResult;
    }
}
