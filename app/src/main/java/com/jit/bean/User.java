package com.jit.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * author : XZQ
 * date   : 2019/12/3
 * description    :
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private String email;

    private Date register_time;

    private String description;

    private String wechat;

    private String github;

    private Integer follow;

    private Integer fans;

    private String follow_ids;

    private String fans_ids;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
