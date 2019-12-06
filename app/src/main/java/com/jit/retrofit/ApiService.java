package com.jit.retrofit;

import com.jit.bean.Blog;
import com.jit.bean.BlogDto;
import com.jit.bean.User;

import java.util.List;

import okhttp3.ResponseBody;
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

    /**
     * 根据userId查询User
     *
     * @param userId
     * @return
     */
    @GET("app/user/{id}")
    Call<User> getUserById(@Path("id") Integer userId);

    /**
     * 查询所有用户
     *
     * @return
     */
    @GET("app/user/list")
    Call<List<User>> listUser();

    /**
     * 处理用户登录
     *
     * @param user
     * @return
     */
    @POST("app/login")
    Call<Integer> login(@Body User user);

    /**
     * 处理用户注册
     *
     * @param user
     * @return
     */
    @POST("app/login")
    Call<Integer> register(@Body User user);













    /*Blog*/

    /**
     * 新建blog
     *
     * @param blog
     * @return
     */
    @POST("app/blog/input")
    Call<Integer> postBlog(@Body Blog blog);

    /**
     * 删除blog
     *
     * @param blogId
     * @return
     */
    @DELETE("app/blog/{id}")
    Call<Integer> deleteBlog(@Path("id") Integer blogId);

    /**
     * 修改blog
     *
     * @param blog
     * @return
     */
    @PUT("app/blog")
    Call<Integer> updateBlog(@Body Blog blog);

    /**
     * 根据blogId查询Blog
     *
     * @param blogId
     * @return
     */
    @GET("app/blog/{id}")
    Call<BlogDto> getBlogById(@Path("id") Integer blogId);

    /**
     * 查询推荐的blog（随机）
     *
     * @return
     */
    @GET("app/blog/randList")
    Call<List<BlogDto>> getRandList();

    /**
     * 根据userId查询其所有博客（动态）
     *
     * @param userId
     * @return
     */
    @GET("app/blog/user/{id}")
    Call<List<BlogDto>> getUserBlogs(@Path("id") Integer userId);

    /**
     * 查询相关博客
     *
     * @param str
     * @return
     */
    @GET("app/blog/search")
    Call<List<BlogDto>> searchBlog(@Query("search") String str);


    @GET("/image/ico_07.jpg")
//    @Streaming
    Call<ResponseBody> getImg();
}
