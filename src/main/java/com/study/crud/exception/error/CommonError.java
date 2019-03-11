package com.study.crud.exception.error;

/**
 * Desc: 通用错误的信息处理接口
 *
 * @author qianqian.zhang
 * @date 2019-03-11 13:23
 **/
public interface CommonError {
    int getErrCode();
    String getErrMsg();

    /**
     * 自定义错误信息
     * 通过定制化的东西改动错误信息
     * @return
     */
    CommonError setErrMsg(String errMsg);
}
