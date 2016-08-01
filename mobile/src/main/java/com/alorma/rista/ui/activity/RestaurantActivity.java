package com.alorma.rista.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import com.alorma.rista.R;
import com.alorma.rista.domain.places.FoursquarePlace;
import com.alorma.rista.domain.places.FoursquarePlaceVenue;
import com.alorma.rista.presenter.PlacePresenter;
import com.alorma.rista.ui.ToolbarColorizeHelper;
import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class RestaurantActivity extends AppCompatActivity implements PlacePresenter.PlaceCallback {

  private static final String EXTRA_ID = "EXTRA_ID";
  private static final String EXTRA_NAME = "EXTRA_NAME";
  private static final String EXTRA_PHOTO = "EXTRA_PHOTO";

  private Toolbar toolbar;

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

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    String name = getIntent().getStringExtra(EXTRA_NAME);
    setTitle(name);

    ImageView photoImage = (ImageView) findViewById(R.id.image);

    String photo = getIntent().getStringExtra(EXTRA_PHOTO);
    if (photo != null) {
      loadimage(photoImage, photo);
    }

    String id = getIntent().getStringExtra(EXTRA_ID);

    PlacePresenter presenter = new PlacePresenter(getString(R.string.foursquare_client_id), getString(R.string.foursquare_client_secret));

    presenter.load(id, this);
  }

  private void loadimage(ImageView photoImage, String photo) {
    BitmapRequestBuilder<String, Bitmap> glide =
        Glide.with(this).load(photo).asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL);
    glide.listener(new RequestListener<String, Bitmap>() {
      @Override
      public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
        return false;
      }

      @Override
      public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache,
          boolean isFirstResource) {
        Palette.Builder builder = Palette.from(resource);
        Palette palette = builder.generate();
        onPaletteLoaded(palette);
        return false;
      }
    }).into(photoImage);
  }

  private void onPaletteLoaded(Palette palette) {
    if (palette.getVibrantSwatch() != null) {
      int titleTextColor = palette.getVibrantSwatch().getTitleTextColor();
      int rgb = palette.getVibrantSwatch().getRgb();

      toolbar.setBackgroundColor(rgb);

      ToolbarColorizeHelper.colorizeToolbar(toolbar, titleTextColor);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        getWindow().setStatusBarColor(darker(rgb, 0.7f));
      }
    }
  }

  public int darker(int color, float factor) {
    int a = Color.alpha(color);
    int r = Color.red(color);
    int g = Color.green(color);
    int b = Color.blue(color);

    return Color.argb(a, Math.max((int) (r * factor), 0), Math.max((int) (g * factor), 0), Math.max((int) (b * factor), 0));
  }

  @Override
  public void onPlaceLoaded(FoursquarePlaceVenue foursquarePlace) {

  }
}
