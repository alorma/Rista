package com.alorma.rista.domain.places;

import java.util.HashMap;
import java.util.Map;

public class FoursquarePlaceMapper {
  public Map<String, Object> toMap(FoursquarePlace place) {
    HashMap<String, Object> result = new HashMap<>();
    result.put("id", place.getVenue().getId());
    result.put("name", place.getVenue().getName());
    for (FoursquarePhotos.Group group : place.getVenue().getPhotos().getGroups()) {
      for (FoursquarePhotos.Group.Item item : group.getItems()) {
        Map<String, Object> map = mapPhotos(item);
        result.put(item.getId(), map);
      }
    }
    return result;
  }

  private Map<String, Object> mapPhotos(FoursquarePhotos.Group.Item photo) {
    HashMap<String, Object> result = new HashMap<>();
    result.put("id", photo.getId());
    result.put("prefix", photo.getPrefix());
    result.put("suffix", photo.getSuffix());
    result.put("widht", photo.getWidth());
    result.put("height", photo.getHeight());
    return result;
  }
}
