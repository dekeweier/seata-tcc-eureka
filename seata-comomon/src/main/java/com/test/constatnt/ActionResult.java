package com.test.constatnt;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Create by 李传伟 on 2022/1/8 13:33
 */

@Accessors(chain = true)
@Data
public class ActionResult<T> implements Serializable{


    private static final long serialVersionUID = 2652890474905256361L;

    private int code;

    private String msg;

    private T data;

    /**
     * 返回成功数据
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ActionResult success(T data){
        return new ActionResult().setCode(ConstantEnum.SUCCESS.getCode()).setMsg(ConstantEnum.SUCCESS.getMsg()).setData(data);
    }

    /**
     * 返回失败数据
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ActionResult fail(T data){
        return new ActionResult().setCode(ConstantEnum.FAIL.getCode()).setMsg(ConstantEnum.FAIL.getMsg()).setData(data);
    }



}
