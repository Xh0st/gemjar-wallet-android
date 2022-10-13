package com.gemjarwallet.app.repository;

import com.gemjarwallet.app.entity.coinbasepay.DestinationWallet;

import java.util.List;

public interface CoinbasePayRepositoryType {
    String getUri(DestinationWallet.Type type, String json, List<String> list);
}
