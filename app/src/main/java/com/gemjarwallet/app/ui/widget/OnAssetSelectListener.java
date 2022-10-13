package com.gemjarwallet.app.ui.widget;


import com.gemjarwallet.app.entity.nftassets.NFTAsset;

import java.math.BigInteger;

public interface OnAssetSelectListener
{
    void onAssetSelected(BigInteger tokenId, NFTAsset asset, int position);
    void onAssetUnselected();
}
