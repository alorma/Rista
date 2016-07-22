package com.alorma.rista.domain.places;

import java.util.List;
import rx.Observable;

public class PlacesRepository {

  private PlacesDatasource placesApi;

  public PlacesRepository(PlacesDatasource placesApi) {
    this.placesApi = placesApi;
  }

  public Observable<List<FoursquarePlace>> getPlaces(double latitude, double longitude) {
    return placesApi.getPlaces(latitude, longitude);
  }

  public Observable<List<FoursquarePlace>> getPlaces(double latitude, double longitude, int offset) {
    return placesApi.getPlaces(latitude, longitude, offset);
  }
}
