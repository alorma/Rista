package com.alorma.rista.domain.accounts;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GetAccountInteractor {

  public AppAccount getAccount() {
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    if (currentUser != null) {
      AppAccount appAccount = new AppAccount();
      appAccount.setName(currentUser.getDisplayName());
      appAccount.setEmail(currentUser.getEmail());
      appAccount.setPhoto(currentUser.getPhotoUrl());
      appAccount.setUid(currentUser.getUid());
      return appAccount;
    }
    return null;
  }
}
