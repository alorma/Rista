package com.alorma.rista.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.alorma.rista.R;
import com.alorma.rista.domain.places.FoursquarePlace;
import com.alorma.rista.presenter.PlacesPresenter;
import com.alorma.rista.ui.adapter.PlacesAdapter;
import com.alorma.rista.ui.adapter.RecyclerArrayAdapter;
import com.alorma.rista.ui.utils.FoursquarePhotosHelper;
import com.bumptech.glide.Glide;
import java.util.List;
import java.util.Map;

public class PlacesActivity extends AppCompatActivity
    implements PlacesPresenter.PlacesCallback, RecyclerArrayAdapter.RecyclerAdapterContentListener, PlacesAdapter.AdapterCallback {

  private static final int LOCATION_PERMISSION_REQUEST = 1121;
  private PlacesPresenter placesPresenter;
  private PlacesAdapter placesAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_places);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    placesPresenter = new PlacesPresenter(getString(R.string.foursquare_client_id), getString(R.string.foursquare_client_secret));

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

    placesAdapter = new PlacesAdapter(Glide.with(this), getLayoutInflater());
    placesAdapter.setRecyclerAdapterContentListener(this);
    placesAdapter.setAdapterCallback(this);

    recyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.places_columns)));
    recyclerView.setAdapter(placesAdapter);

    placesPresenter.load(this);
  }

  @Override
  public void requestUserLogin() {
    Intent intent = LoginActivity.createIntent(this);
    startActivity(intent);
  }

  @Override
  public boolean checkPermission() {
    return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
  }

  @Override
  public void requestPermission() {
    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
      Toast.makeText(this, "Hey! Locate me!", Toast.LENGTH_SHORT).show();
    }
    ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, LOCATION_PERMISSION_REQUEST);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    switch (requestCode) {
      case LOCATION_PERMISSION_REQUEST: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          placesPresenter.load(this);
        } else {
          Toast.makeText(this, "Oh :( I can't load places", Toast.LENGTH_SHORT).show();
        }
        return;
      }
    }
  }

  @Override
  public void requestLocation() {
    // Acquire a reference to the system Location Manager
    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

    // Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
      public void onLocationChanged(Location location) {
        placesPresenter.changeLocation(location);
      }

      public void onStatusChanged(String provider, int status, Bundle extras) {
      }

      public void onProviderEnabled(String provider) {
      }

      public void onProviderDisabled(String provider) {
      }
    };

    // Register the listener with the Location Manager to receive location updates
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    placesPresenter.changeLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
  }

  @Override
  public void onPlacesLoaded(List<FoursquarePlace> foursquarePlaces) {
    placesAdapter.addAll(foursquarePlaces);
  }

  @Override
  public void loadMoreItems() {
    placesPresenter.loadMore(placesAdapter.getItemCount(), this);
  }

  @Override
  public void updateFavorites(Map<String, FoursquarePlace> placesCache) {
    placesAdapter.setFavorites(placesCache.keySet());
  }

  @Override
  public void onItemSelected(FoursquarePlace place, View imageTransitionView, View textTransitionView) {
    Uri uri = new FoursquarePhotosHelper().buildPhoto(place.getVenue().getPhotos().getGroups().get(0).getItems().get(0));
    Intent intent = RestaurantActivity.createIntent(this, place.getVenue().getId(), place.getVenue().getName(), uri.toString());

    ActivityOptionsCompat options = ActivityOptionsCompat.
        makeSceneTransitionAnimation(this, imageTransitionView, getString(R.string.transition_restaurant_photo));

    ActivityCompat.startActivity(this, intent, options.toBundle());
  }

  @Override
  public void onFavCallback(FoursquarePlace place) {
    placesPresenter.onFavItem(place);
  }
}
