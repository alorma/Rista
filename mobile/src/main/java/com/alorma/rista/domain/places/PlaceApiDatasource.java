package com.alorma.rista.domain.places;

import android.support.annotation.NonNull;
import com.alorma.rista.domain.base.ApiException;
import com.alorma.rista.domain.base.RestProvider;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;

public class PlaceApiDatasource implements PlacesDatasource {

  private RestProvider<PlacesRetrofitService> restProvider;

  public PlaceApiDatasource(RestProvider<PlacesRetrofitService> restProvider) {
    this.restProvider = restProvider;
  }

  @Override
  public Observable<List<FoursquarePlace>> getPlaces(double latitude, double longitude) {
    return null;
  }

  @NonNull
  private Observable<FoursquareExplorePlaceResponse> getResponse(String venueId) {
    Call<FoursquareExplorePlaceResponse> call = restProvider.getService().getPlace(venueId);
    try {
      Response<FoursquareExplorePlaceResponse> response = call.execute();
      if (response.isSuccessful()) {
        return Observable.just(response.body());
      } else {
        return Observable.error(new ApiException());
      }
    } catch (IOException e) {
      return Observable.error(e);
    }
  }

  @Override
  public Observable<List<FoursquarePlace>> getPlaces(double latitude, double longitude, int offset) {
    return null;
  }

  @Override
  public Observable<FoursquarePlace> getPlace(String venueId) {
    return getDefer(venueId);
  }

  @NonNull
  private Observable<FoursquarePlace> getDefer(String venueId) {
    return Observable.defer(() -> getResponse(venueId).map(FoursquareExplorePlaceResponse::getResponse)
        .map(ExplorePlaceResponse::getVenue)
        .map(FoursquarePlace::new));
  }
}
