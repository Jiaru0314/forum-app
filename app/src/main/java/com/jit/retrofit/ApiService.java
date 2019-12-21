package com.jit.retrofit;

import com.jit.bean.Blog;
import com.jit.bean.BlogDto;
import com.jit.bean.Comment;
import com.jit.bean.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * author : XZQ
 * date   : 2019/12/3
 * description    :
 */
public interface ApiService {
    /*User*/

    /*根据userId查询User*/
    @GET("app/user/{id}")
    Call<User> getUserById(@Path("id") Integer userId);

    /*查询所有用户*/
    @GET("app/user/list")
    Call<List<User>> listUser();

    /*通过UserId查询其所有关注者*/
    @GET("app/user/follows/{id}")
    Call<List<User>> listFollows(@Path("id") Integer userId);

    /*通过UserId查询其所有粉丝*/
    @GET("app/user/fans/{id}")
    Call<List<User>> listFans(@Path("id") Integer userId);

    /*处理用户登录*/
    @POST("app/user/login")
    Call<Integer> login(@Body User user);

    /*处理用户注册*/
    @POST("app/user/register")
    Call<Integer> register(@Body User user);

    /*Blog*/

    /*新建blog*/
    @POST("app/blog")
    Call<Integer> postBlog(@Body Blog blog);

    /*删除blog*/
    @DELETE("app/blog/{id}")
    Call<Integer> deleteBlog(@Path("id") Integer blogId);

    /*修改blog*/
    @PUT("app/blog")
    Call<Integer> updateBlog(@Body Blog blog);

    /*根据blogId查询Blog*/
    @GET("app/blog/{id}")
    Call<BlogDto> getBlogById(@Path("id") Integer blogId);

    /*查询推荐的Blog（随机）*/
    @GET("app/blog/randList")
    Call<List<BlogDto>> getRandList();

    /*查询关注用户的Blog*/
    @GET("app/blog/followBlogList/{id}")
    Call<List<BlogDto>> getFollowBlogList(@Path("id") Integer userId);

    /*查询最新发布的Blog*/
    @GET("app/blog/newestBlogList")
    Call<List<BlogDto>> getNewestBlogList();

    /*根据userId查询其所有博客（动态）*/
    @GET("app/blog/user/{id}")
    Call<List<BlogDto>> getUserBlogs(@Path("id") Integer userId);

    /*查询相关博客*/
    @GET("app/blog/search")
    Call<List<BlogDto>> searchBlog(@Query("search") String str);

    /*comment*/

    /*新增评论*/
    @POST("app/comment")
    Call<Integer> addComment(@Body Comment comment);

    /*删除评论*/
    @DELETE("app/comment/{id}")
    Call<Integer> deleteComment(@Path("id") Integer commentId);

}
