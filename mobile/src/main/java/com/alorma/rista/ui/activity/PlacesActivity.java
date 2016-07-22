package com.alorma.rista.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.alorma.rista.R;
import com.alorma.rista.domain.places.FoursquarePlace;
import com.alorma.rista.presenter.PlacesPresenter;
import com.alorma.rista.ui.activity.LoginActivity;
import com.alorma.rista.ui.adapter.PlacesAdapter;
import com.alorma.rista.ui.adapter.RecyclerArrayAdapter;
import com.bumptech.glide.Glide;
import java.util.List;

public class PlacesActivity extends AppCompatActivity implements PlacesPresenter.PlacesCallback,
    RecyclerArrayAdapter.RecyclerAdapterContentListener {

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

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
}
