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
public class Comment {

    private Integer id;

    private Integer user_id;

    private String content;

    private Date createTime;

    private Integer blog_id;

    private Integer parent_id;

    private String username;

    private String avatar;

    private List<Comment> replyComments;

    public Comment(Integer user_id, String content, Date createTime, Integer blog_id) {
        this.user_id = user_id;
        this.content = content;
        this.createTime = createTime;
        this.blog_id = blog_id;
    }
}
