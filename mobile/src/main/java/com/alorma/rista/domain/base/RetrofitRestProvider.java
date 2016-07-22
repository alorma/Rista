package com.alorma.rista.domain.base;

import android.support.annotation.NonNull;
import com.alorma.rista.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitRestProvider<K> implements RestProvider<K> {
  private CredentialsProvider credentialsProvider;
  private String endpoint;
  private Retrofit retrofit;

  public RetrofitRestProvider(String endpoint, CredentialsProvider credentialsProvider) {
    this.credentialsProvider = credentialsProvider;
    this.endpoint = endpoint;
  }

  private Retrofit build() {
    return new Retrofit.Builder().baseUrl(endpoint).addConverterFactory(GsonConverterFactory.create()).client(getOkClient()).build();
  }

  @Override
  public K getService() {
    return getService(build());
  }

  protected abstract K getService(Retrofit retrofit);

  @NonNull
  private OkHttpClient getOkClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();

    builder.addInterceptor(chain -> {
      Request original = chain.request();

      HttpUrl url = original.url()
          .newBuilder()
          .addQueryParameter("client_id", credentialsProvider.clientId())
          .addQueryParameter("client_secret", credentialsProvider.clientSecret())
          .addQueryParameter("v", "20160822")
          .addQueryParameter("m", "foursquare")
          .build();

      Request.Builder requestBuilder = original.newBuilder().url(url);

      Request request = requestBuilder.build();
      return chain.proceed(request);
    });

    if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      //builder.addInterceptor(interceptor);
    }

    return builder.build();
  }
}
