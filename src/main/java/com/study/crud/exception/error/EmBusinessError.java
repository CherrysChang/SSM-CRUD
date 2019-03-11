package com.study.crud.exception.error;

/**
 * Desc: 错误信息 枚举类
 *  方便统一管理错误码
 * @author qianqian.zhang
 * @date 2019-03-11 13:25
 **/
public enum EmBusinessError implements CommonError{
    //定义错误码和错误信息
    //通用错误类型。例：10001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    //自定义未知错误
    UNKNOW_ERROR(10002,"未知错误"),
    //例：20000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001,"用户不存在")
    //后期继续延伸错误信息enum值……
    ;

    /**
     * 枚举类是可以拥有成员变量属性的，因为枚举本质上就是一个面向对象的类
     */
    private int errCode;//错误码
    private String errMsg;//错误信息

    private EmBusinessError(int errCode, String errMsg) {
        this.errCode=errCode;
        this.errMsg=errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg=errMsg;
        return this;
    }
}
