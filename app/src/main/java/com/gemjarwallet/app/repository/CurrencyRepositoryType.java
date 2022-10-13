package com.gemjarwallet.app.repository;

import com.gemjarwallet.app.entity.CurrencyItem;

import java.util.ArrayList;

public interface CurrencyRepositoryType {
    String getDefaultCurrency();

    void setDefaultCurrency(String currency);

    ArrayList<CurrencyItem> getCurrencyList();
}
