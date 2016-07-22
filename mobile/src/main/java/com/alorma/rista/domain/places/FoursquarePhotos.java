package com.alorma.rista.domain.places;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class FoursquarePhotos {

  @SerializedName("count") private int count;
  @SerializedName("groups") private List<Group> groups;

  public FoursquarePhotos() {
  }

  public FoursquarePhotos(List<Group.Item> photos) {
    groups = new ArrayList<>();
    groups.add(new Group(photos));
  }

  public int getCount() {
    return count;
  }

  public List<Group> getGroups() {
    return groups;
  }

  public static class Group {

    @SerializedName("type") private String type;
    @SerializedName("name") private String name;
    @SerializedName("count") private int count;
    @SerializedName("items") private List<Item> items;
    public Group() {
    }

    public Group(List<Item> photos) {
      this.items = photos;
    }

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

    public static class Item {
      @SerializedName("id") private String id;
      @SerializedName("prefix") private String prefix;
      @SerializedName("suffix") private String suffix;
      @SerializedName("width") private long width;
      @SerializedName("height") private long height;

      public Item() {
      }

      public Item(String id, String prefix, String suffix, long height, long width) {
        this.id = id;
        this.prefix = prefix;
        this.suffix = suffix;
        this.height = height;
        this.width = width;
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

      public long getWidth() {
        return width;
      }

      public long getHeight() {
        return height;
      }
    }
  }
}
