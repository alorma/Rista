package com.alorma.rista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.alorma.rista.presenter.PlacesPresenter;
import com.alorma.rista.ui.activity.LoginActivity;

public class PlacesActivity extends AppCompatActivity implements PlacesPresenter.PlacesCallback {

  private PlacesPresenter placesPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_places);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    placesPresenter = new PlacesPresenter();
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
}
