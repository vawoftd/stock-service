package com.vawo.foundation.stock.exception;

import com.vawo.foundation.stock.utils.result.BaseResult;
import com.vawo.foundation.stock.utils.result.BaseResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;


@RestControllerAdvice
public class BusinessExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(BusinessExceptionHandler.class);

    @ExceptionHandler({BusinessException.class})
    @ResponseBody
    public BaseResult<String> errorHandler(BusinessException ex) {
        BaseResult<String> baseResult = BaseResultUtils.buildEmptyBaseResult();
        baseResult.setErrorCode(ex.getCode().toString());
        baseResult.setErrorMsg(ex.getMessage());
        //如果不是普通的异常码
        if (ex.getCause() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = null;
            try {
                String open = System.getenv("stock.statck.detail");
                if (!StringUtils.isEmpty(open)) {
                    pw = new PrintWriter(sw);
                    ex.getCause().printStackTrace(pw);
                    baseResult.setDetail(sw.toString());
                }
            } catch (Exception e) {
                logger.error("error occurs when handle response exception:", e);
            } finally {
                if (pw != null) {
                    pw.close();
                }
            }
        }
        return baseResult;
    }
}
