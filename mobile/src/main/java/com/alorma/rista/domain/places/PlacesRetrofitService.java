package com.alorma.rista.domain.places;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesRetrofitService {
  @GET("venues/explore?venuePhotos=1&limit=50")
  Call<FoursquareExploreResponse> getPlaces(@Query("ll") String ll, @Query("section") String section, @Query("query") String query, @Query("offset") int offset);
}
