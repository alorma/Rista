package com.alorma.rista.domain.places;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ExplorePlacesResponse {

  @SerializedName("groups")
  private List<ExploreGroupsResponse> groups;

  public List<ExploreGroupsResponse> getGroups() {
    return groups;
  }
}
