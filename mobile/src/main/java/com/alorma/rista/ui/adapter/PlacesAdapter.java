package com.alorma.rista.ui.adapter;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alorma.rista.R;
import com.alorma.rista.domain.places.FoursquarePlace;
import com.alorma.rista.ui.utils.FoursquarePhotosHelper;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.RequestManager;
import com.varunest.sparkbutton.SparkButton;
import java.util.Set;

public class PlacesAdapter extends RecyclerArrayAdapter<FoursquarePlace, PlacesAdapter.PlaceHolder> {
  private final FoursquarePhotosHelper foursquarePhotosHelper;
  private RequestManager glide;
  private Set<String> favorites;

  public PlacesAdapter(RequestManager glide, LayoutInflater inflater) {
    super(inflater);
    this.glide = glide;
    foursquarePhotosHelper = new FoursquarePhotosHelper();
  }

  @Override
  protected PlaceHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
    return new PlaceHolder(inflater.inflate(R.layout.place_row, parent, false));
  }

  @Override
  protected void onBindViewHolder(PlaceHolder holder, FoursquarePlace foursquarePlace) {
    Uri uri = foursquarePhotosHelper.buildPhoto(foursquarePlace.getVenue().getPhotos().getGroups().get(0).getItems().get(0));
    Uri thumbnailUri = foursquarePhotosHelper.buildPhoto(foursquarePlace.getVenue().getPhotos().getGroups().get(0).getItems().get(0));

    DrawableRequestBuilder<Uri> color = glide.load(thumbnailUri).override(1, 1);
    DrawableRequestBuilder<Uri> thumbnail = glide.load(thumbnailUri).thumbnail(color);

    glide.load(uri).thumbnail(thumbnail).crossFade().centerCrop().into(holder.imageView);

    holder.textView.setText(foursquarePlace.getVenue().getName());
    if (favorites != null) {
      holder.favorite.setChecked(favorites.contains(foursquarePlace.getVenue().getId()));
    }
  }

  public void setFavorites(Set<String> favorites) {
    this.favorites = favorites;
    notifyDataSetChanged();
  }

  public class PlaceHolder extends RecyclerView.ViewHolder {
    CardView cardView;
    ImageView imageView;
    SparkButton favorite;
    TextView textView;

    public PlaceHolder(View itemView) {
      super(itemView);

      cardView = (CardView) itemView.findViewById(R.id.cardView);
      imageView = (ImageView) itemView.findViewById(R.id.image);
      favorite = (SparkButton) itemView.findViewById(R.id.favorite);
      textView = (TextView) itemView.findViewById(R.id.text);

      favorite.setEventListener((button, buttonState) -> {
        if (buttonState) {
          if (getCallback() != null) {
            getCallback().onItemSelected(getItem(getAdapterPosition()));
          }
        }
      });
    }
  }
}
