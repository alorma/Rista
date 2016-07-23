package com.alorma.rista.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import com.alorma.rista.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class RestaurantActivity extends AppCompatActivity {

  private static final String EXTRA_ID = "EXTRA_ID";
  private static final String EXTRA_NAME = "EXTRA_NAME";
  private static final String EXTRA_PHOTO = "EXTRA_PHOTO";

  public static Intent createIntent(Context context, String id, String name, String photo) {
    Intent intent = new Intent(context, RestaurantActivity.class);
    intent.putExtra(EXTRA_ID, id);
    intent.putExtra(EXTRA_NAME, name);
    intent.putExtra(EXTRA_PHOTO, photo);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ImageView photoImage = (ImageView) findViewById(R.id.image);

    String photo = getIntent().getStringExtra(EXTRA_PHOTO);
    if (photo != null) {
      Glide.with(this).load(photo).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(photoImage);
    }
  }
}
