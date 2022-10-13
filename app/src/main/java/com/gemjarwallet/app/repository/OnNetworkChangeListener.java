package com.gemjarwallet.app.repository;

import com.gemjarwallet.app.entity.NetworkInfo;

public interface OnNetworkChangeListener {
	void onNetworkChanged(NetworkInfo networkInfo);
}
