package com.alorma.rista.domain.places;

import com.google.gson.annotations.SerializedName;

public class FoursquarePlaceVenue {
  public FoursquarePlaceVenue() {
  }

  @SerializedName("id")
  private String id;
  @SerializedName("name")
  private String name;
  @SerializedName("photos")
  private FoursquarePhotos photos;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public FoursquarePhotos getPhotos() {
    return photos;
  }
}
