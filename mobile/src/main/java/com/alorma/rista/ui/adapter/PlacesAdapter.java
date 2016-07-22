package com.alorma.rista.ui.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.alorma.rista.R;
import com.alorma.rista.domain.places.FoursquarePlace;
import com.alorma.rista.ui.utils.FoursquarePhotosHelper;
import com.bumptech.glide.RequestManager;

public class PlacesAdapter extends RecyclerArrayAdapter<FoursquarePlace, PlacesAdapter.PlaceHolder> {
  private final FoursquarePhotosHelper foursquarePhotosHelper;
  private RequestManager glide;

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

    glide.load(uri).crossFade().centerCrop().into(holder.imageView);
  }

  public class PlaceHolder extends RecyclerView.ViewHolder {
    ImageView imageView;

    public PlaceHolder(View itemView) {
      super(itemView);

      imageView = (ImageView) itemView.findViewById(R.id.image);

      itemView.setOnClickListener(view -> {
        if (getCallback() != null) {
          getCallback().onItemSelected(getItem(getAdapterPosition()));
        }
      });
    }
  }
}
