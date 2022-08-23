package com.make.common.exception;

/**
 * @Author make
 * @Description
 * @Date 2022/8/19 17:36
 */

import org.springframework.security.core.AuthenticationException;

public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg) {
        super(msg);
    }
}
