package com.gemjarwallet.app.entity;
import com.gemjarwallet.app.entity.cryptokeys.KeyEncodingType;
import com.gemjarwallet.app.service.KeyService;

public interface ImportWalletCallback
{
    void walletValidated(String address, KeyEncodingType type, KeyService.AuthenticationLevel level);
}
