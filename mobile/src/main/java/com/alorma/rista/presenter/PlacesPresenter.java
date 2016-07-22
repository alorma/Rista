package com.alorma.rista.presenter;

import com.alorma.rista.domain.accounts.AppAccount;
import com.alorma.rista.domain.accounts.GetAccountInteractor;
import com.alorma.rista.domain.base.CredentialsProvider;
import com.alorma.rista.domain.places.FoursquarePlace;
import com.alorma.rista.domain.places.FoursquarePlaceMapper;
import com.alorma.rista.domain.places.GetPlacesInteractor;
import com.alorma.rista.domain.places.PlacesApiDatasource;
import com.alorma.rista.domain.places.PlacesDatasource;
import com.alorma.rista.domain.places.PlacesRepository;
import com.alorma.rista.domain.places.RetrofitPlaces;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PlacesPresenter {

  private final GetPlacesInteractor interactor;

  private DatabaseReference placesRef;
  private PlacesCallback callback;
  private FoursquarePlaceMapper foursquarePlaceMapper;
  private Map<String, FoursquarePlace> placesCache = new HashMap<>();

  public PlacesPresenter(String clientId, String clientSecret) {
    foursquarePlaceMapper = new FoursquarePlaceMapper();

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

      FirebaseDatabase database = FirebaseDatabase.getInstance();
      placesRef = database.getReference(account.getUid() + "/places");
      placesRef.keepSynced(true);

      placesRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
          if (value != null) {
            Map<String, FoursquarePlace> places = new HashMap<>();
            for (String key : value.keySet()) {
              FoursquarePlace place = foursquarePlaceMapper.fromMap((HashMap<String, Object>) value.get(key));
              places.put(key, place);
            }
            placesCache = places;
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });

      Observable<List<FoursquarePlace>> observable = interactor.getPlaces(41.3914706, 2.1352049);
      execute(observable);
    }
  }

  private void execute(Observable<List<FoursquarePlace>> observable) {
    observable.flatMap(Observable::from)
        .map(this::checkExist)
        .toList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::places, this::error);
  }

  private FoursquarePlace checkExist(FoursquarePlace place) {
    place.setFavorite(placesCache.containsKey(place.getVenue().getId()));
    return place;
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
    Observable<List<FoursquarePlace>> observable = interactor.getPlaces(41.3914706, 2.1352049, itemCount);
    execute(observable);
  }

  public void savePlace(FoursquarePlace foursquarePlace) {
    Map<String, Object> map = foursquarePlaceMapper.toMap(foursquarePlace);

    Map<String, Object> childUpdates = new HashMap<>();
    childUpdates.put(foursquarePlace.getVenue().getId(), map);

    placesRef.updateChildren(childUpdates);
  }

  public interface PlacesCallback {
    void requestUserLogin();

    void onPlacesLoaded(List<FoursquarePlace> foursquarePlaces);
  }
}
