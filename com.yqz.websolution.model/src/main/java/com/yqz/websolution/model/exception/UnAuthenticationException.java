package com.yqz.websolution.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "sorry,you are unauthorized.")
public class UnAuthenticationException extends Exception{

}
