package com.alorma.rista.domain.places;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FoursquarePhotos {
  public FoursquarePhotos() {
  }

  @SerializedName("count") private int count;
  @SerializedName("groups") private List<Group> groups;

  public int getCount() {
    return count;
  }

  public List<Group> getGroups() {
    return groups;
  }

  public class Group {
    public Group() {
    }

    @SerializedName("type") private String type;
    @SerializedName("name") private String name;
    @SerializedName("count") private int count;
    @SerializedName("items") private List<Item> items;

    public String getType() {
      return type;
    }

    public String getName() {
      return name;
    }

    public int getCount() {
      return count;
    }

    public List<Item> getItems() {
      return items;
    }

    public class Item {
      @SerializedName("id") private String id;
      @SerializedName("prefix") private String prefix;
      @SerializedName("suffix") private String suffix;
      @SerializedName("width") private int width;
      @SerializedName("height") private int height;

      public Item() {
      }

      public String getId() {
        return id;
      }

      public String getPrefix() {
        return prefix;
      }

      public String getSuffix() {
        return suffix;
      }

      public int getWidth() {
        return width;
      }

      public int getHeight() {
        return height;
      }
    }
  }
}
