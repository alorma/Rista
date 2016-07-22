package com.alorma.rista.presenter;

import com.alorma.rista.domain.accounts.AppAccount;
import com.alorma.rista.domain.accounts.GetAccountInteractor;
import com.alorma.rista.domain.base.CredentialsProvider;
import com.alorma.rista.domain.places.FoursquarePlace;
import com.alorma.rista.domain.places.GetPlacesInteractor;
import com.alorma.rista.domain.places.PlacesApiDatasource;
import com.alorma.rista.domain.places.PlacesDatasource;
import com.alorma.rista.domain.places.PlacesRepository;
import com.alorma.rista.domain.places.RetrofitPlaces;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PlacesPresenter {

  private final GetPlacesInteractor interactor;
  private PlacesCallback callback;

  public PlacesPresenter(String clientId, String clientSecret) {
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
    PlacesDatasource api = new PlacesApiDatasource(new RetrofitPlaces("https://api.foursquare.com/v2/", credentials));
    PlacesRepository placesRepository = new PlacesRepository(api);
    interactor = new GetPlacesInteractor(placesRepository);
  }

  public void load(PlacesCallback callback) {
    this.callback = callback;
    GetAccountInteractor getAccountInteractor = new GetAccountInteractor();

    AppAccount account = getAccountInteractor.getAccount();

    if (account == null) {
      if (callback != null) {
        callback.requestUserLogin();
      }
    } else {
      interactor.getPlaces(41.3914706, 2.1352049)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(this::places, this::error);
    }
  }

  private void places(List<FoursquarePlace> foursquarePlaces) {
    if (callback != null) {
      callback.onPlacesLoaded(foursquarePlaces);
    }
  }

  private void error(Throwable throwable) {

  }

  public void loadMore(int itemCount, PlacesCallback callback) {
    this.callback = callback;
    interactor.getPlaces(41.3914706, 2.1352049, itemCount)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::places, this::error);
  }

  public interface PlacesCallback {
    void requestUserLogin();

    void onPlacesLoaded(List<FoursquarePlace> foursquarePlaces);
  }
}
