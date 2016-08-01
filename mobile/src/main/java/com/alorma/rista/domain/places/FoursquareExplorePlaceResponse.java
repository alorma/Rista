package com.alorma.rista.domain.places;

import com.google.gson.annotations.SerializedName;

public class FoursquareExplorePlaceResponse {

  @SerializedName("meta")
  private MetaResponse meta;
  @SerializedName("response")
  private ExplorePlaceResponse response;

  public MetaResponse getMeta() {
    return meta;
  }

  public ExplorePlaceResponse getResponse() {
    return response;
  }
}
