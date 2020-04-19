package com.vawo.foundation.demo.utils.result;

import com.vawo.foundation.demo.exception.BusinessException;

public interface BaseEnumI {

    String getValue();

    String getCode();

    default BusinessException buildException() {
        return new BusinessException(getCode(), getValue(), null);
    }

    default BusinessException businessException(Throwable e) {
        return new BusinessException(getCode(), getValue(), e);
    }
}
