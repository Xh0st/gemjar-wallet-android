package com.gemjarwallet.shadows;

import android.content.Context;

import com.gemjarwallet.app.entity.AnalyticsProperties;
import com.gemjarwallet.app.service.AnalyticsServiceType;
import com.gemjarwallet.app.service.KeyService;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(KeyService.class)
public class ShadowKeyService
{
    @Implementation
    public void __constructor__(Context ctx, AnalyticsServiceType<AnalyticsProperties> analyticsService) {

    }
}
