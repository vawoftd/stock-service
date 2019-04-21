package com.vawo.foundation.demo.exception;


public class SmsException extends Exception {

        private static final long serialVersionUID = 8149189020770229330L;

        public SmsException(String msg, Throwable cause) {
                super(msg, cause);
        }
        
        public SmsException(String msg) {
                super(msg);
        }

}
