package com.gemjarwallet.app.ui.widget;

import java.io.Serializable;

import com.gemjarwallet.app.entity.DApp;

public interface OnDappClickListener extends Serializable {
    void onDappClick(DApp dapp);
}
