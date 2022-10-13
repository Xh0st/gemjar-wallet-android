package com.gemjarwallet.shadows;

import android.content.Context;

import com.gemjarwallet.app.web3.JsInjectorClient;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(JsInjectorClient.class)
public class ShadowJsInjectorClient
{
    @Implementation
    protected void __constructor__(Context context) {
    }
}
