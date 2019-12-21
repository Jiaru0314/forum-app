package com.jit.bean;

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
public class Blog {

    private Integer id;

    private String title;

    private String content;

    private Date create_time;

    private Integer user_id;

    private Integer views;

    private Integer prefers;

    private Boolean is_commentabled;

    private Boolean is_public;

    private String tagIds;

    private Integer type_id;

    public Blog(String title, String content, Date create_time, Integer user_id, Boolean is_commentabled, Boolean is_public, String tagIds, Integer type_id) {
        this.title = title;
        this.content = content;
        this.create_time = create_time;
        this.user_id = user_id;
        this.is_commentabled = is_commentabled;
        this.is_public = is_public;
        this.tagIds = tagIds;
        this.type_id = type_id;
    }
}