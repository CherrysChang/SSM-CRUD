package com.study.crud.exception;

/**
 * Desc: 登录失败异常类
 *
 * @author qianqian.zhang
 * @date 2018-05-10 11:17
 **/
public class UnLoginException extends RuntimeException{

    public UnLoginException() {
        super();
    }

    public UnLoginException(String message) {
        super(message);
    }
}
