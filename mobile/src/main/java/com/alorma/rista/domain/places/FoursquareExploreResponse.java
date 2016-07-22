package com.alorma.rista.domain.places;

import com.google.gson.annotations.SerializedName;

public class FoursquareExploreResponse {

  @SerializedName("meta")
  private MetaResponse meta;
  @SerializedName("response")
  private ExploreResponse response;

  public MetaResponse getMeta() {
    return meta;
  }

  public ExploreResponse getResponse() {
    return response;
  }
}
