package com.gemjarwallet.app.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gemjarwallet.app.C;
import com.gemjarwallet.app.entity.AnalyticsProperties;
import com.gemjarwallet.app.entity.Wallet;
import com.gemjarwallet.app.entity.coinbasepay.DestinationWallet;
import com.gemjarwallet.app.interact.GenericWalletInteract;
import com.gemjarwallet.app.repository.CoinbasePayRepositoryType;
import com.gemjarwallet.app.service.AnalyticsServiceType;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.disposables.Disposable;

@HiltViewModel
public class CoinbasePayViewModel extends BaseViewModel
{
    private final GenericWalletInteract genericWalletInteract;
    private final CoinbasePayRepositoryType coinbasePayRepository;
    private final AnalyticsServiceType analyticsService;
    private final MutableLiveData<Wallet> defaultWallet = new MutableLiveData<>();
    private final MutableLiveData<byte[]> signature = new MutableLiveData<>();

    protected Disposable disposable;

    @Inject
    public CoinbasePayViewModel(GenericWalletInteract genericWalletInteract,
                                CoinbasePayRepositoryType coinbasePayRepository,
                                AnalyticsServiceType analyticsService)
    {
        this.genericWalletInteract = genericWalletInteract;
        this.coinbasePayRepository = coinbasePayRepository;
        this.analyticsService = analyticsService;
    }

    public LiveData<Wallet> defaultWallet()
    {
        return defaultWallet;
    }

    public void prepare()
    {
        progress.postValue(false);
        disposable = genericWalletInteract
                .find()
                .subscribe(this::onDefaultWallet, this::onError);
    }

    private void onDefaultWallet(final Wallet wallet)
    {
        defaultWallet.setValue(wallet);
    }

    public String getUri(DestinationWallet.Type type, String address, List<String> list)
    {
        AnalyticsProperties analyticsProperties = new AnalyticsProperties();
        analyticsProperties.setData(type.toString() + ": " + list.get(0));
        analyticsService.track(C.AN_USE_COINBASE_PAY, analyticsProperties);
        return coinbasePayRepository.getUri(type, address, list);
    }
}