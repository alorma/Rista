package com.alorma.rista.domain.places;

import java.util.List;
import rx.Observable;

public interface PlacesDatasource {
  Observable<List<FoursquarePlace>> getPlaces(double latitude, double longitude);

  Observable<List<FoursquarePlace>> getPlaces(double latitude, double longitude, int offset);
}
