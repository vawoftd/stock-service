package com.vawo.foundation.demo.utils;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: pengyujia
 * Date: 2019/3/11
 * Time: 下午11:29
 * To change this template use File | Settings | File Templates.
 */
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
