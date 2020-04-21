package com.vawo.foundation.stock.utils.result;


import com.vawo.foundation.stock.exception.BusinessException;

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
