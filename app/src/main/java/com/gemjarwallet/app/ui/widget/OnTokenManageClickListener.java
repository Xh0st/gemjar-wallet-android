package com.gemjarwallet.app.ui.widget;

import com.gemjarwallet.app.entity.tokens.Token;

public interface OnTokenManageClickListener
{
    void onTokenClick(Token token, int position, boolean isChecked);
}
