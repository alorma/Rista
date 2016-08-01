package com.alorma.rista.ui.utils;

import android.net.Uri;
import com.alorma.rista.domain.places.FoursquarePhotos;

public class FoursquarePhotosHelper {

  public Uri buildPhoto(FoursquarePhotos.Group.Item item) {
    return Uri.parse(item.getPrefix())
        .buildUpon()
        .appendPath("600x600")
        .appendEncodedPath(item.getSuffix().substring(1, item.getSuffix().length()))
        .build();
  }
}
