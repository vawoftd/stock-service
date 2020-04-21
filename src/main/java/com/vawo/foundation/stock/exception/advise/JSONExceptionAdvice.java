package com.vawo.foundation.stock.exception.advise;

import com.alibaba.fastjson.JSONException;
import com.vawo.foundation.stock.utils.result.BaseResult;
import com.vawo.foundation.stock.utils.result.BaseResultUtils;
import com.vawo.foundation.stock.utils.result.CommonCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JSONExceptionAdvice {

    private Logger logger = LoggerFactory.getLogger(JSONException.class);

    @NotEmpty
    @ExceptionHandler({JSONException.class})
    @ResponseBody
    public BaseResult<String> errorHandler(JSONException ex) {
        logger.error("JSON解析错误", ex);
        BaseResult<String> baseResult = BaseResultUtils.buildBaseResult(CommonCodeEnum.PARAMETER_VALIDATE_FAILED);
        String message = ex.getMessage();
        if(StringUtils.startsWith(message, "can not cast")) {
            baseResult.setErrorMsg("JSON类型转换错误");
        }
        else {
            baseResult.setErrorMsg("JSON解析错误");
        }
        return baseResult;
    }

}



