package com.gemjarwallet.app.repository;

import com.gemjarwallet.app.entity.OnRampContract;
import com.gemjarwallet.app.entity.tokens.Token;

public interface OnRampRepositoryType {
    String getUri(String address, Token token);

    OnRampContract getContract(Token token);
}
