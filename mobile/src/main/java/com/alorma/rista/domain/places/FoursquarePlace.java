package com.alorma.rista.domain.places;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FoursquarePlace {

  @SerializedName("venue")
  private FoursquarePlaceVenue venue;
  private boolean favorite;

  public FoursquarePlace(String id, String name, List<FoursquarePhotos.Group.Item> photos) {
    venue = new FoursquarePlaceVenue(id, name, photos);
  }

  public FoursquarePlaceVenue getVenue() {
    return venue;
  }

  public void setFavorite(boolean favorite) {
    this.favorite = favorite;
  }

  public boolean isFavorite() {
    return favorite;
  }
}
