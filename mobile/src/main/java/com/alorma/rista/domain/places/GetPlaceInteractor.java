package com.alorma.rista.domain.places;

import rx.Observable;

public class GetPlaceInteractor {

  private PlacesRepository placesRepository;

  public GetPlaceInteractor(PlacesRepository placesRepository) {
    this.placesRepository = placesRepository;
  }

  public Observable<FoursquarePlace> getPlace(String placeId) {
    return placesRepository.getPlace(placeId);
  }
}
