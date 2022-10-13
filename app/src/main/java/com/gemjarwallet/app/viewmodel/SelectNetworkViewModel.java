package com.gemjarwallet.app.viewmodel;

import android.app.Activity;
import android.content.Intent;

import com.gemjarwallet.app.entity.NetworkInfo;
import com.gemjarwallet.app.repository.EthereumNetworkBase;
import com.gemjarwallet.app.repository.EthereumNetworkRepositoryType;
import com.gemjarwallet.app.repository.PreferenceRepositoryType;
import com.gemjarwallet.app.service.TokensService;
import com.gemjarwallet.app.ui.SelectNetworkFilterActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SelectNetworkViewModel extends BaseViewModel {
    private final EthereumNetworkRepositoryType networkRepository;
    private final TokensService tokensService;
    private final PreferenceRepositoryType preferenceRepository;

    @Inject
    public SelectNetworkViewModel(EthereumNetworkRepositoryType ethereumNetworkRepositoryType,
                                  TokensService tokensService,
                                  PreferenceRepositoryType preferenceRepository)
    {
        this.networkRepository = ethereumNetworkRepositoryType;
        this.tokensService = tokensService;
        this.preferenceRepository = preferenceRepository;
    }

    public NetworkInfo[] getNetworkList()
    {
        return networkRepository.getAvailableNetworkList();
    }

    public List<Long> getFilterNetworkList()
    {
        return networkRepository.getFilterNetworkList();
    }

    public void openSelectNetworkFilters(Activity ctx, int requestCode)
    {
        Intent intent = new Intent(ctx, SelectNetworkFilterActivity.class);
        ctx.startActivityForResult(intent, requestCode);
    }

    public boolean mainNetActive()
    {
        return preferenceRepository.isActiveMainnet();
    }

    public boolean hasShownTestNetWarning()
    {
        return preferenceRepository.hasShownTestNetWarning();
    }

    public void setShownTestNetWarning()
    {
        preferenceRepository.setShownTestNetWarning();
    }

    public NetworkInfo getNetworkByChain(long chainId)
    {
        return networkRepository.getNetworkByChain(chainId);
    }

    public boolean isMainNet(long networkId)
    {
        return EthereumNetworkBase.hasRealValue(networkId);
    }

    public long getSelectedNetwork()
    {
        NetworkInfo browserNetwork = networkRepository.getActiveBrowserNetwork();
        if (browserNetwork != null) { return browserNetwork.chainId; }
        else return -1;
    }

    public TokensService getTokensService() {
        return tokensService;
    }
}
