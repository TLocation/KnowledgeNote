package com.location.retrofitsample;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

	public class HttpLogger implements HttpLoggingInterceptor.Logger {
		@Override
		public void log(String message) {
			Log.d("HttpLogInfo", message);
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//日志拦截器
		HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
		logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient okHttpClient =new OkHttpClient.Builder()
				.addNetworkInterceptor(logInterceptor).build();
		Retrofit build = new Retrofit.Builder()
				//设置baseurl
				.baseUrl("https://wanandroid.com/")
				//设置Gson解析器
				.addConverterFactory(GsonConverterFactory.create())
				.client(okHttpClient)
				.build();
		Api api = build.create(Api.class);

		api.getList().enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				Log.i("TestRetrofit","on suc");
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				Log.i("TestRetrofit","on error ="+t.getMessage());
			}
		});
		api.getNotes().enqueue(new Callback<NoteBean>() {
			@Override
			public void onResponse(Call<NoteBean> call, Response<NoteBean> response) {
				Log.i("TestRetrofit","data size="+response.body().getData().getDatas().size());

			}

			@Override
			public void onFailure(Call<NoteBean> call, Throwable t) {

			}
		});



		api.login("tianxiaolong","tianxiaolong").enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {

			}
		});
	}



}
