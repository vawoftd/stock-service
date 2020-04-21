package com.vawo.foundation.stock.exception;

import com.vawo.foundation.stock.utils.JSONUtil;
import com.vawo.foundation.stock.utils.result.CommonCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Optional;

@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ExceptionAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

    private int argMaxLength = 2000;


    @Pointcut("execution(* com.sensetime..*Controller.*(..)) ")
    public void exceptionController() {

    }

    @Around("exceptionController()")
    public Object aroundService(ProceedingJoinPoint joinPoint) {
        Object[] args = null;
        try {
            if (Objects.nonNull(joinPoint.getArgs())) {
                args = joinPoint.getArgs();
            }
            return joinPoint.proceed();
        } catch (BusinessException | ConstraintViolationException e) {
            //向上剖说明底层已经处理完毕
            throw e;
        } catch (Throwable e) {
            if (e instanceof IllegalStateException && StringUtils.startsWith(e.getMessage(), "No instances available for ")) {
                throw new BusinessException(CommonCodeEnum.SYSTEM_FAILED.getCode(), e.getMessage(), e);
            } else {
                String argStr = buildArgs(args);
                logger.error("execute method failed|class:{}|method:{}|args:{}",
                        Optional.ofNullable(joinPoint.getSignature().getDeclaringTypeName()).orElse(""),
                        Optional.ofNullable(joinPoint.getSignature().getName()).orElse(""),
                        Optional.ofNullable(argStr).orElse(""),
                        e);
                throw new BusinessException(CommonCodeEnum.SYSTEM_FAILED.getCode(), CommonCodeEnum.SYSTEM_FAILED.getValue(), e);
            }
        }
    }

    private String buildArgs(Object[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (Object arg : args) {
                //预防一些对象序列化不了的情况
                try {
                    String argStr = JSONUtil.toJSONString(arg);
                    if (argStr != null && argStr.length() > argMaxLength) {
                        argStr = argStr.substring(0, argMaxLength);
                    }
                    stringBuilder.append(argStr);
                    stringBuilder.append("|");
                } catch (Exception e) {
                    stringBuilder.append("unSerializable");
                    stringBuilder.append("|");
                }
            }
        }
        return stringBuilder.toString();
    }

}
