package com.alorma.rista.presenter;

import com.alorma.rista.domain.base.CredentialsProvider;
import com.alorma.rista.domain.places.FoursquarePlace;
import com.alorma.rista.domain.places.FoursquarePlaceVenue;
import com.alorma.rista.domain.places.GetPlaceInteractor;
import com.alorma.rista.domain.places.PlaceApiDatasource;
import com.alorma.rista.domain.places.PlacesDatasource;
import com.alorma.rista.domain.places.PlacesRepository;
import com.alorma.rista.domain.places.RetrofitPlaces;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PlacePresenter {

  private final GetPlaceInteractor interactor;

  private PlaceCallback callback;

  public PlacePresenter(String clientId, String clientSecret) {
    CredentialsProvider credentials = new CredentialsProvider() {
      @Override
      public String clientId() {
        return clientId;
      }

      @Override
      public String clientSecret() {
        return clientSecret;
      }
    };
    PlacesDatasource api = new PlaceApiDatasource(new RetrofitPlaces("https://api.foursquare.com/v2/", credentials));
    PlacesRepository placesRepository = new PlacesRepository(api);
    interactor = new GetPlaceInteractor(placesRepository);
  }

  public void load(String placeId, PlaceCallback callback) {
    this.callback = callback;
    interactor.getPlace(placeId)
        .map(FoursquarePlace::getVenue)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::place, Throwable::printStackTrace);
  }

  private void place(FoursquarePlaceVenue place) {
    if (callback != null) {
      callback.onPlaceLoaded(place);
    }
  }

  public interface PlaceCallback {
    void onPlaceLoaded(FoursquarePlaceVenue foursquarePlace);
  }
}
