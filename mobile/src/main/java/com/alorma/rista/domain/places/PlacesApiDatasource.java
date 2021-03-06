package com.alorma.rista.domain.places;

import android.support.annotation.NonNull;
import com.alorma.rista.domain.base.ApiException;
import com.alorma.rista.domain.base.RestProvider;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;

public class PlacesApiDatasource implements PlacesDatasource {

  private RestProvider<PlacesRetrofitService> restProvider;

  public PlacesApiDatasource(RestProvider<PlacesRetrofitService> restProvider) {
    this.restProvider = restProvider;
  }

  @Override
  public Observable<List<FoursquarePlace>> getPlaces(double latitude, double longitude) {
    return getDefer(latitude, longitude, 0);
  }

  @NonNull
  private Observable<FoursquareExplorePlacesResponse> getResponse(double latitude, double longitude, int offset) {
    String ll = latitude + "," + longitude;
    Call<FoursquareExplorePlacesResponse> call = restProvider.getService().getPlaces(ll, "food", "restaurant", offset);
    try {
      Response<FoursquareExplorePlacesResponse> response = call.execute();
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
    return getDefer(latitude, longitude, offset);
  }

  @Override
  public Observable<FoursquarePlace> getPlace(String id) {
    return null;
  }

  @NonNull
  private Observable<List<FoursquarePlace>> getDefer(double latitude, double longitude, int offset) {
    return Observable.defer(() -> getResponse(latitude, longitude, offset).map(FoursquareExplorePlacesResponse::getResponse)
        .map(ExplorePlacesResponse::getGroups)
        .flatMap(Observable::from)
        .map(ExploreGroupsResponse::getItems));
  }
}
