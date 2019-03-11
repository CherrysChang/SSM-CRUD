package com.study.crud.utils;

/**
 * Desc: 定义 通用的返回对象
 *
 * @author qianqian.zhang
 * @date 2019-03-11 11:23
 **/
public class CommonReturnType {
    /**
     * 表明对应请求的返回处理结果 “success” 或 “fail”
     */
    private String status;
    /**
     * 若 status=success，则data内返回前端需要的json数据
     * 若 status=fail，则data内使用通用的错误码格式
     */
    private Object data;

    /**
     * 定义一个通用的创建方法
     * 定义一个默认的成功的处理，只传数据的方法，默认认为是请求正确的
     * @param result
     * @return
     */
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }

    /**
     * 定义一个通用的创建方法
     * 调用这个静态方法，传入返回数据和对应的状态码
     * @param result
     * @param status
     * @return
     */
    public static CommonReturnType create(Object result, String status) {
        CommonReturnType resultType=new CommonReturnType();
        resultType.setStatus(status);
        resultType.setData(result);
        return resultType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
