package com.study.crud.exception;

import com.study.crud.exception.error.CommonError;

/**
 * Desc: 这种设计模式为包装器业务异常类实现
 *          BusinessException 和 EmBusinessError 共同继承了 CommonError接口对应的方法，
 *          以至于外部不仅可以通过 new EmBusinessError()或者new BusinessException()都可以有errCode和errMsg对应的组装定义，
 *          并且需要共同实现setErrMsg方法，可以用于将原本enum定义的errMsg给覆盖掉
 * @author qianqian.zhang
 * @date 2019-03-11 13:49
 **/
public class BusinessException extends RuntimeException implements CommonError {

    private CommonError commonError;

    /**
     * 直接接收EmBusinessError的传参用于构造业务异常
     * @param commonError
     */
    public BusinessException(CommonError commonError){
        super();
        this.commonError=commonError;
    }

    /**
     * 接收自定义errMsg的方式构造业务异常
     * @param commonError
     * @param errMsg
     */
    public BusinessException(CommonError commonError,String errMsg){
        super();
        this.commonError=commonError;
        this.commonError.setErrMsg(errMsg);
    }

    /**
     * 异常类中获取错误码和错误信息
     * @return
     */
    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
