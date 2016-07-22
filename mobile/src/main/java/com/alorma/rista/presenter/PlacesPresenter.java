package com.alorma.rista.presenter;

import com.alorma.rista.domain.accounts.AppAccount;
import com.alorma.rista.domain.accounts.GetAccountInteractor;

public class PlacesPresenter {

  private PlacesCallback callback;

  public void load(PlacesCallback callback) {
    GetAccountInteractor getAccountInteractor = new GetAccountInteractor();

    AppAccount account = getAccountInteractor.getAccount();

    if (account == null) {
      callback.requestUserLogin();
    }
  }

  public interface PlacesCallback {
    void requestUserLogin();
  }
}
