package com.alorma.rista.domain.places;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ExploreGroupsResponse {
  @SerializedName("items")
  private List<FoursquarePlace> items;

  public List<FoursquarePlace> getItems() {
    return items;
  }
}
