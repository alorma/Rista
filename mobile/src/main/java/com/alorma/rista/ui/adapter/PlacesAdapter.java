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
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.varunest.sparkbutton.SparkButton;
import java.util.Set;

public class PlacesAdapter extends RecyclerArrayAdapter<FoursquarePlace, PlacesAdapter.PlaceHolder> {
  private final FoursquarePhotosHelper foursquarePhotosHelper;
  private RequestManager glide;
  private Set<String> favorites;
  private AdapterCallback adapterCallback;

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

    glide.load(uri).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);

    holder.textView.setText(foursquarePlace.getVenue().getName());
    if (favorites != null) {
      boolean state = favorites.contains(foursquarePlace.getVenue().getId());
      holder.favorite.setChecked(state);
    }
  }

  public void setFavorites(Set<String> favorites) {
    this.favorites = favorites;
    notifyDataSetChanged();
  }

  public void setAdapterCallback(AdapterCallback adapterCallback) {
    this.adapterCallback = adapterCallback;
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
        if (adapterCallback != null) {
          adapterCallback.onFavCallback(getItem(getAdapterPosition()));
        }
      });

      itemView.setOnClickListener(view -> {
        if (adapterCallback != null) {
          FoursquarePlace item = getItem(getAdapterPosition());
          adapterCallback.onItemSelected(item, imageView, textView);
        }
      });
    }
  }

  public interface AdapterCallback {
    void onItemSelected(FoursquarePlace place, View imageTransitionView, View textTransitionView);

    void onFavCallback(FoursquarePlace place);
  }
}
