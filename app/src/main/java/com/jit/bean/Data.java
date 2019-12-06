package com.jit.bean;

/**
 * author : XZQ
 * date   : 2019/12/3
 * description    :
 */
@lombok.Data
public class Data<T> {
    private int status;
    private String msg;
    private T data;
}
