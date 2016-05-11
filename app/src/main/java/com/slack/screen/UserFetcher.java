package com.slack.screen;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.slack.screen.model.User;
import com.slack.screen.model.UserList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import static android.content.Context.MODE_PRIVATE;

public class UserFetcher {
  interface UserService {
    @GET("users.list") Call<UserList> users();
  }

  public static final Gson gson = new GsonBuilder().create();

  private static final String BASE_URL = "https://slack.com/api/";
  private static final String API_TOKEN = "xoxp-5048173296-5048487710-19045732087-b5427e3b46";
  private static final UserFetcher fetcher = new UserFetcher();

  private UserFetcher() {
  }

  public static void fetchAndUpdate(Context context, final UserAdapter adapter) {
    final SharedPreferences prefs = context.getSharedPreferences("slack-screen", MODE_PRIVATE);
    adapter.update(getUsersFromCache(prefs));

    fetcher.getUserService().users().enqueue(new Callback<UserList>() {
      @Override public void onResponse(Call<UserList> call, retrofit2.Response<UserList> response) {
        if (response.isSuccessful()) {
          adapter.update(response.body().members);
          writeToCache(prefs, gson.toJson(response.body().members));
        }
      }

      @Override public void onFailure(Call<UserList> call, Throwable t) {
        adapter.update(getUsersFromCache(prefs));
      }
    });
  }

  private static void writeToCache(SharedPreferences prefs, String json) {
    prefs.edit().putString("user-cache", json).apply();
  }

  private static List<User> getUsersFromCache(SharedPreferences prefs) {
    String json = prefs.getString("user-cache", null);
    if (json == null) return new ArrayList<>();
    return gson.fromJson(json, new TypeToken<List<User>>() {
    }.getType());
  }

  private UserService getUserService() {
    OkHttpClient okHttpClient = new OkHttpClient.Builder() //
        .addInterceptor(new Interceptor() {
          @Override public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder().addQueryParameter("token", API_TOKEN).build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
          }
        }) //
        .build();

    Retrofit restAdapter = new Retrofit.Builder() //
        .client(okHttpClient) //
        .addConverterFactory(GsonConverterFactory.create()) //
        .baseUrl(BASE_URL) //
        .build();
    return restAdapter.create(UserService.class);
  }
}
