package com.location.retrofitsample;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author tianxiaolong
 * time：2020/6/22 21:56
 * description：
 */
public interface Api {

	/**
	 * get 请求
	 * @return
	 */
	@GET("wxarticle/chapters/json")
	Call<ResponseBody> getList();

	/**
	 * get 请求 使用GsonConvert解析器
	 *
	 * 添加参数使用@Query
	 * 添加Header @Header
	 * @return
	 */
	@GET("wxarticle/list/408/1/json")
	Call<NoteBean> getNotes();


	/**
	 * post 表单提交  带参数
	 * json参数 @Boday
	 * @param userName
	 * @param pwd
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/login")
	Call<ResponseBody> login(@Field("username") String userName, @Field("password") String pwd);

}
