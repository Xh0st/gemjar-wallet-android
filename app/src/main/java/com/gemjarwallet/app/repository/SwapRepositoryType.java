package com.gemjarwallet.app.repository;

import com.gemjarwallet.app.entity.lifi.SwapProvider;

import java.util.List;

public interface SwapRepositoryType
{
    public List<SwapProvider> getProviders();
}
