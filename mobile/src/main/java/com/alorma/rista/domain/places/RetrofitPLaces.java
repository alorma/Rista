package com.alorma.rista.domain.places;

import com.alorma.rista.domain.base.CredentialsProvider;
import com.alorma.rista.domain.base.RetrofitRestProvider;
import retrofit2.Retrofit;

public class RetrofitPlaces extends RetrofitRestProvider<PlacesRetrofitService> {

  public RetrofitPlaces(String endpoint, CredentialsProvider credentialsProvider) {
    super(endpoint, credentialsProvider);
  }

  @Override
  protected PlacesRetrofitService getService(Retrofit retrofit) {
    return retrofit.create(PlacesRetrofitService.class);
  }
}
