package com.vawo.foundation.stock.utils;

import java.util.ResourceBundle;

public class I18nMessageUtils {

    public static String getValidateMessage(String key) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("validate/message");
        String i18nMessage = null;
        if (resourceBundle != null) {
            if (key != null && resourceBundle.containsKey(key)) {
                i18nMessage = resourceBundle.getString(key);
            }
        }
        return i18nMessage;

    }

}
