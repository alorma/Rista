package com.alorma.rista.domain.places;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoursquarePlaceMapper {
  public Map<String, Object> toMap(FoursquarePlace place) {
    HashMap<String, Object> result = new HashMap<>();
    result.put("id", place.getVenue().getId());
    result.put("name", place.getVenue().getName());
    Map<String, Map<String, Object>> photos = new HashMap<>();
    for (FoursquarePhotos.Group group : place.getVenue().getPhotos().getGroups()) {
      for (FoursquarePhotos.Group.Item item : group.getItems()) {
        Map<String, Object> map = mapPhotos(item);
        photos.put(item.getId(), map);
      }
    }

    result.put("photos", photos);
    return result;
  }

  private Map<String, Object> mapPhotos(FoursquarePhotos.Group.Item photo) {
    HashMap<String, Object> result = new HashMap<>();
    result.put("id", photo.getId());
    result.put("prefix", photo.getPrefix());
    result.put("suffix", photo.getSuffix());
    result.put("width", photo.getWidth());
    result.put("height", photo.getHeight());
    return result;
  }

  public FoursquarePlace fromMap(HashMap<String, Object> value) {
    String id = (String) value.get("id");
    String name = (String) value.get("name");
    Map<String, Map<String, Object>> photosList = (HashMap<String, Map<String, Object>>) value.get("photos");
    List<FoursquarePhotos.Group.Item> photos = new ArrayList<>();
    if (photosList != null) {
      for (String key : photosList.keySet()) {
        FoursquarePhotos.Group.Item item = mapPhoto(photosList.get(key));
        photos.add(item);
      }
    }
    return new FoursquarePlace(id, name, photos);
  }

  private FoursquarePhotos.Group.Item mapPhoto(Map<String, Object> photo) {
    String id = (String) photo.get("id");
    String prefix = (String) photo.get("prefix");
    String suffix = (String) photo.get("suffix");
    long width = (Long) photo.get("height");
    long height = (Long) photo.get("width");
    return new FoursquarePhotos.Group.Item(id, prefix, suffix, height, width);
  }
}
