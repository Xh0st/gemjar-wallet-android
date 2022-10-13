package com.gemjarwallet.app.web3;

import com.gemjarwallet.token.entity.EthereumMessage;

public interface OnSignPersonalMessageListener {
    void onSignPersonalMessage(EthereumMessage message);
}
