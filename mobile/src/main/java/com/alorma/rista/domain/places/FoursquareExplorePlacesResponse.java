package com.alorma.rista.domain.places;

import com.google.gson.annotations.SerializedName;

public class FoursquareExplorePlacesResponse {

  @SerializedName("meta")
  private MetaResponse meta;
  @SerializedName("response")
  private ExplorePlacesResponse response;

  public MetaResponse getMeta() {
    return meta;
  }

  public ExplorePlacesResponse getResponse() {
    return response;
  }
}
