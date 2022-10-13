package com.gemjarwallet.shadows;


import com.gemjarwallet.app.di.mock.KeyProviderMockNonProductionImpl;
import com.gemjarwallet.app.repository.KeyProvider;
import com.gemjarwallet.app.repository.KeyProviderFactory;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(KeyProviderFactory.class)
public class ShadowKeyProviderFactoryNonProduction
{
    @Implementation
    public static KeyProvider get() {
        return new KeyProviderMockNonProductionImpl();
    }
}
