package com.alorma.rista.domain.accounts;

import android.net.Uri;

public class AppAccount {
  private String name;
  private String email;
  private Uri photo;
  private String uid;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setPhoto(Uri photo) {
    this.photo = photo;
  }

  public Uri getPhoto() {
    return photo;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getUid() {
    return uid;
  }
}
