package com.alorma.rista.domain.places;

import java.util.List;
import rx.Observable;

public class GetPlacesInteractor {

  private PlacesRepository placesRepository;

  public GetPlacesInteractor(PlacesRepository placesRepository) {
    this.placesRepository = placesRepository;
  }

  public Observable<List<FoursquarePlace>> getPlaces(double latitude, double longitude) {
    return placesRepository.getPlaces(latitude, longitude);
  }

  public Observable<List<FoursquarePlace>> getPlaces(double latitude, double longitude, int offset) {
    return placesRepository.getPlaces(latitude, longitude, offset);
  }
}
