package com.gemjarwallet.app.router;

import android.content.Context;
import android.content.Intent;

import com.gemjarwallet.app.C;
import com.gemjarwallet.app.ui.HomeActivity;

public class HomeRouter {
    public void open(Context context, boolean isClearStack) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(C.FROM_HOME_ROUTER, C.FROM_HOME_ROUTER); //HomeRouter should restart the app at the wallet
        if (isClearStack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(intent);
    }
}
