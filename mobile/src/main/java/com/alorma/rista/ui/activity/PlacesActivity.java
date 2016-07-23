package com.alorma.rista.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
  }

  @Override
  protected void onStart() {
    super.onStart();
    placesPresenter.load(this);
  }

  @Override
  public void requestUserLogin() {
    Intent intent = LoginActivity.createIntent(this);
    startActivity(intent);
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
  public void onItemSelected(FoursquarePlace place, View transitionView) {
    Uri uri = new FoursquarePhotosHelper().buildPhoto(place.getVenue().getPhotos().getGroups().get(0).getItems().get(0));
    Intent intent = RestaurantActivity.createIntent(this, place.getVenue().getId(), place.getVenue().getName(), uri.toString());

    ActivityOptionsCompat options = ActivityOptionsCompat.
        makeSceneTransitionAnimation(this, transitionView, getString(R.string.transition_restaurant_photo));

    ActivityCompat.startActivity(this, intent, options.toBundle());
  }

  @Override
  public void onFavCallback(FoursquarePlace place) {
    placesPresenter.onFAvItem(place);
  }
}
