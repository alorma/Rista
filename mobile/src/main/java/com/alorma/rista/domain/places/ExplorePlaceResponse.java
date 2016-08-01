package com.alorma.rista.domain.places;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ExplorePlaceResponse {

  @SerializedName("venue")
  private FoursquarePlaceVenue venue;

  public FoursquarePlaceVenue getVenue() {
    return venue;
  }
}
