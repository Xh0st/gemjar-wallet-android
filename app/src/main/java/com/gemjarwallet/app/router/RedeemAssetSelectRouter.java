package com.gemjarwallet.app.router;

import android.content.Context;
import android.content.Intent;

import com.gemjarwallet.app.C;
import com.gemjarwallet.app.ui.RedeemAssetSelectActivity;
import com.gemjarwallet.app.entity.tokens.Token;

/**
 * Created by James on 27/02/2018.
 */

public class RedeemAssetSelectRouter
{
    public void open(Context context, Token token) {
        Intent intent = new Intent(context, RedeemAssetSelectActivity.class);
        intent.putExtra(C.EXTRA_CHAIN_ID, token.tokenInfo.chainId);
        intent.putExtra(C.EXTRA_ADDRESS, token.getAddress());
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        context.startActivity(intent);
    }
}
