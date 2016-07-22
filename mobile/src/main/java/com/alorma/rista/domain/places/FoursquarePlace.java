package com.alorma.rista.domain.places;

import com.google.gson.annotations.SerializedName;

public class FoursquarePlace {

  public FoursquarePlace() {
  }

  @SerializedName("venue")
  private FoursquarePlaceVenue venue;

  public FoursquarePlaceVenue getVenue() {
    return venue;
  }
}
