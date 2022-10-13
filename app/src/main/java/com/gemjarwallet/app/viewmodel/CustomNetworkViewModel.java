package com.gemjarwallet.app.viewmodel;

import com.gemjarwallet.app.repository.EthereumNetworkRepository;
import com.gemjarwallet.app.repository.EthereumNetworkRepositoryType;
import com.gemjarwallet.app.entity.NetworkInfo;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CustomNetworkViewModel extends BaseViewModel
{
    private final EthereumNetworkRepositoryType ethereumNetworkRepository;

    @Inject
    CustomNetworkViewModel(
            EthereumNetworkRepositoryType ethereumNetworkRepository)
    {
        this.ethereumNetworkRepository = ethereumNetworkRepository;
    }

    public void saveNetwork(String name, String rpcUrl, long chainId, String symbol, String blockExplorerUrl, String explorerApiUrl, boolean isTestnet, Long oldChainId) {
        this.ethereumNetworkRepository.saveCustomRPCNetwork(name, rpcUrl, chainId, symbol, blockExplorerUrl, explorerApiUrl, isTestnet, oldChainId);
    }

    public NetworkInfo getNetworkInfo(long chainId) {
        return this.ethereumNetworkRepository.getNetworkByChain(chainId);
    }

    public boolean isTestNetwork(NetworkInfo network) {
        return !EthereumNetworkRepository.hasRealValue(network.chainId);
    }

    public NetworkInfo getBuiltInNetwork(long chainId)
    {
        return this.ethereumNetworkRepository.getBuiltInNetwork(chainId);
    }
}
