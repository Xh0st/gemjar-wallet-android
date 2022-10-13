package com.gemjarwallet.app.entity.tokenscript;

import com.gemjarwallet.token.entity.XMLDsigDescriptor;

public class TokenScriptFileData
{
    public String hash;
    public XMLDsigDescriptor sigDescriptor;

    public TokenScriptFileData()
    {
        hash = null;
        sigDescriptor = null;
    }
}
