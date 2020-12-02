package com.say.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ResponseWrapper
 * @Description: 接口统一返回格式
 * @Author: lel
 * @Date: 2020/11/17 21:14
 * @Version: v1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseWrapper {
    /**是否成功*/
    private boolean success;
    /**返回码*/
    private Integer code;
    /**返回信息*/
    private String msg;
    /**返回数据*/
//    private Object data;
    private Map data = new HashMap<String, Object>();

    /**
     * 自定义返回结果
     * 建议使用统一的返回结果，特殊情况可以使用此方法
     * @param success
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static ResponseWrapper markCustom(boolean success, Integer code, String msg, String data){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccess(success);
        responseWrapper.setCode(code);
        responseWrapper.setMsg(msg);
        return responseWrapper;
    }

    /**
     * 参数为空或者参数格式错误
     * @return
     */

    public static ResponseWrapper markParamError(){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccess(false);
        responseWrapper.setCode(ReturnCode.PARAMS_ERROR.getCode());
        responseWrapper.setMsg(ReturnCode.PARAMS_ERROR.getMsg());
        return responseWrapper;
    }

    /**
     * 查询失败
     * @return
     */
    public static ResponseWrapper markError(){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccess(false);
        responseWrapper.setCode(ReturnCode.FEAILED.getCode());
        responseWrapper.setMsg(ReturnCode.FEAILED.getMsg());
        responseWrapper.setData(null);
        return responseWrapper;
    }

    /**
     * 查询成功但无数据
     * @return
     */
    public static ResponseWrapper markSuccessButNoData(){
        ResponseWrapper responseWrapper  = new ResponseWrapper();
        responseWrapper.setSuccess(true);
        responseWrapper.setCode(ReturnCode.NODATA.getCode());
        responseWrapper.setMsg(ReturnCode.NODATA.getMsg());
        responseWrapper.setData(null);
        return responseWrapper;
    }

    /**
     * 查询成功且有数据
     * @param data
     * @return
     */
    public static ResponseWrapper markSuccess(Object data){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccess(true);
        responseWrapper.setCode(ReturnCode.SUCCESS.getCode());
        responseWrapper.setMsg(ReturnCode.SUCCESS.getMsg());
        return responseWrapper;
    }

}