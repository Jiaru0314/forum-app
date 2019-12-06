package com.jit.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author : XZQ
 * date   : 2019/12/3
 * description    :
 */
@Data
@NoArgsConstructor
public class Type {

    private Integer id;

    private String name;

    private Integer counts;

    private List<Type> types = new ArrayList<>();

}

