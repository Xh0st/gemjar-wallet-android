package com.gemjarwallet.shadows;

import com.gemjarwallet.app.di.mock.KeyProviderMockImpl;
import com.gemjarwallet.app.repository.KeyProvider;
import com.gemjarwallet.app.repository.KeyProviderFactory;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(KeyProviderFactory.class)
public class ShadowKeyProviderFactory
{
    @Implementation
    public static KeyProvider get() {
        return new KeyProviderMockImpl();
    }
}
