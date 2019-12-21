package com.jit.bean;

import java.util.Date;
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
public class BlogDto {
    private Integer id;

    private String title;

    private String content;

    private Date create_time;

    private Integer views;

    private Integer prefers;

    private String avatar;

    private String username;

    private String typeName;

    private String tagIds;

    private List<Tag> tags;

    private Integer user_id;

    private Integer type_id;

    private Boolean is_commentabled;

    private List<Comment> comments;

}
