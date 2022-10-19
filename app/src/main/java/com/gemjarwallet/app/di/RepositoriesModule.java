package com.gemjarwallet.app.di;

import static com.gemjarwallet.app.service.KeystoreAccountService.KEYSTORE_FOLDER;

import android.content.Context;

import com.gemjarwallet.app.repository.CoinbasePayRepository;
import com.gemjarwallet.app.repository.CoinbasePayRepositoryType;
import com.gemjarwallet.app.repository.EthereumNetworkRepository;
import com.gemjarwallet.app.repository.EthereumNetworkRepositoryType;
import com.gemjarwallet.app.repository.OnRampRepository;
import com.gemjarwallet.app.repository.OnRampRepositoryType;
import com.gemjarwallet.app.repository.PreferenceRepositoryType;
import com.gemjarwallet.app.repository.SharedPreferenceRepository;
import com.gemjarwallet.app.repository.SwapRepository;
import com.gemjarwallet.app.repository.SwapRepositoryType;
import com.gemjarwallet.app.repository.TokenLocalSource;
import com.gemjarwallet.app.repository.TokenRepository;
import com.gemjarwallet.app.repository.TokenRepositoryType;
import com.gemjarwallet.app.repository.TokensRealmSource;
import com.gemjarwallet.app.repository.TransactionLocalSource;
import com.gemjarwallet.app.repository.TransactionRepository;
import com.gemjarwallet.app.repository.TransactionRepositoryType;
import com.gemjarwallet.app.repository.TransactionsRealmCache;
import com.gemjarwallet.app.repository.WalletDataRealmSource;
import com.gemjarwallet.app.repository.WalletRepository;
import com.gemjarwallet.app.repository.WalletRepositoryType;
import com.gemjarwallet.app.service.AccountKeystoreService;
import com.gemjarwallet.app.service.GemjarWalletService;
import com.gemjarwallet.app.service.AnalyticsService;
import com.gemjarwallet.app.service.AnalyticsServiceType;
import com.gemjarwallet.app.service.AssetDefinitionService;
import com.gemjarwallet.app.service.GasService;
import com.gemjarwallet.app.service.KeyService;
import com.gemjarwallet.app.service.KeystoreAccountService;
import com.gemjarwallet.app.service.NotificationService;
import com.gemjarwallet.app.service.OpenSeaService;
import com.gemjarwallet.app.service.RealmManager;
import com.gemjarwallet.app.service.SwapService;
import com.gemjarwallet.app.service.TickerService;
import com.gemjarwallet.app.service.TokensService;
import com.gemjarwallet.app.service.TransactionsNetworkClient;
import com.gemjarwallet.app.service.TransactionsNetworkClientType;
import com.gemjarwallet.app.service.TransactionsService;
import com.google.gson.Gson;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoriesModule {
	@Singleton
	@Provides
	PreferenceRepositoryType providePreferenceRepository(@ApplicationContext Context context) {
		return new SharedPreferenceRepository(context);
	}

	@Singleton
	@Provides
    AccountKeystoreService provideAccountKeyStoreService(@ApplicationContext Context context, KeyService keyService) {
        File file = new File(context.getFilesDir(), KEYSTORE_FOLDER);
		return new KeystoreAccountService(file, context.getFilesDir(), keyService);
	}

	@Singleton
    @Provides
	TickerService provideTickerService(OkHttpClient httpClient, PreferenceRepositoryType sharedPrefs, TokenLocalSource localSource) {
		return new TickerService(httpClient, sharedPrefs, localSource);
    }

	@Singleton
	@Provides
	EthereumNetworkRepositoryType provideEthereumNetworkRepository(
            PreferenceRepositoryType preferenceRepository,
			@ApplicationContext Context context
            )
    {
        return new EthereumNetworkRepository(preferenceRepository, context);
    }

	@Singleton
	@Provides
    WalletRepositoryType provideWalletRepository(
			PreferenceRepositoryType preferenceRepositoryType,
			AccountKeystoreService accountKeystoreService,
			EthereumNetworkRepositoryType networkRepository,
			WalletDataRealmSource walletDataRealmSource,
			KeyService keyService) {
		return new WalletRepository(
		        preferenceRepositoryType, accountKeystoreService, networkRepository, walletDataRealmSource, keyService);
	}

	@Singleton
	@Provides
	TransactionRepositoryType provideTransactionRepository(
			EthereumNetworkRepositoryType networkRepository,
			AccountKeystoreService accountKeystoreService,
            TransactionLocalSource inDiskCache,
			TransactionsService transactionsService) {
		return new TransactionRepository(
				networkRepository,
				accountKeystoreService,
				inDiskCache,
				transactionsService);
	}

	@Singleton
	@Provides
	OnRampRepositoryType provideOnRampRepository(@ApplicationContext Context context, AnalyticsServiceType analyticsServiceType) {
		return new OnRampRepository(context, analyticsServiceType);
	}

    @Singleton
    @Provides
    SwapRepositoryType provideSwapRepository(@ApplicationContext Context context) {
        return new SwapRepository(context);
    }

    @Singleton
    @Provides
    CoinbasePayRepositoryType provideCoinbasePayRepository() {
        return new CoinbasePayRepository();
    }

	@Singleton
    @Provides
    TransactionLocalSource provideTransactionInDiskCache(RealmManager realmManager) {
        return new TransactionsRealmCache(realmManager);
    }

    @Singleton
    @Provides
    TransactionsNetworkClientType provideBlockExplorerClient(
            OkHttpClient httpClient,
            Gson gson,
            RealmManager realmManager)
    {
        return new TransactionsNetworkClient(httpClient, gson, realmManager);
    }

	@Singleton
    @Provides
    TokenRepositoryType provideTokenRepository(
            EthereumNetworkRepositoryType ethereumNetworkRepository,
            TokenLocalSource tokenLocalSource,
			OkHttpClient httpClient,
			@ApplicationContext Context context,
			TickerService tickerService) {
	    return new TokenRepository(
	            ethereumNetworkRepository,
				tokenLocalSource,
				httpClient,
				context,
				tickerService);
    }

    @Singleton
    @Provides
    TokenLocalSource provideRealmTokenSource(RealmManager realmManager, EthereumNetworkRepositoryType ethereumNetworkRepository) {
	    return new TokensRealmSource(realmManager, ethereumNetworkRepository);
    }

	@Singleton
	@Provides
	WalletDataRealmSource provideRealmWalletDataSource(RealmManager realmManager) {
		return new WalletDataRealmSource(realmManager);
	}

	@Singleton
	@Provides
	TokensService provideTokensService(EthereumNetworkRepositoryType ethereumNetworkRepository,
									   TokenRepositoryType tokenRepository,
									   TickerService tickerService,
									   OpenSeaService openseaService,
									   AnalyticsServiceType analyticsService) {
		return new TokensService(ethereumNetworkRepository, tokenRepository, tickerService, openseaService, analyticsService);
	}

	@Singleton
	@Provides
	TransactionsService provideTransactionsService(TokensService tokensService,
												   EthereumNetworkRepositoryType ethereumNetworkRepositoryType,
												   TransactionsNetworkClientType transactionsNetworkClientType,
												   TransactionLocalSource transactionLocalSource) {
		return new TransactionsService(tokensService, ethereumNetworkRepositoryType, transactionsNetworkClientType, transactionLocalSource);
	}

    @Singleton
    @Provides
    GasService provideGasService(EthereumNetworkRepositoryType ethereumNetworkRepository,
                                 OkHttpClient client,
                                 RealmManager realmManager)
    {
        return new GasService(ethereumNetworkRepository, client, realmManager);
    }

	@Singleton
	@Provides
	OpenSeaService provideOpenseaService() {
		return new OpenSeaService();
	}

	@Singleton
	@Provides
	SwapService provideSwapService() {
		return new SwapService();
	}

	@Singleton
	@Provides
    GemjarWalletService provideFeemasterService(OkHttpClient okHttpClient,
                                               TransactionRepositoryType transactionRepository,
                                               Gson gson) {
		return new GemjarWalletService(okHttpClient, transactionRepository, gson);
	}

	@Singleton
	@Provides
    NotificationService provideNotificationService(@ApplicationContext Context ctx) {
		return new NotificationService(ctx);
	}

	@Singleton
	@Provides
    AssetDefinitionService provideAssetDefinitionService(OkHttpClient okHttpClient, @ApplicationContext Context ctx, NotificationService notificationService, RealmManager realmManager,
														 TokensService tokensService, TokenLocalSource tls, TransactionRepositoryType trt,
                                                         GemjarWalletService gemService) {
		return new AssetDefinitionService(okHttpClient, ctx, notificationService, realmManager, tokensService, tls, trt, gemService);
	}

	@Singleton
	@Provides
	KeyService provideKeyService(@ApplicationContext Context ctx, AnalyticsServiceType analyticsService) {
		return new KeyService(ctx, analyticsService);
	}

	@Singleton
	@Provides
	AnalyticsServiceType provideAnalyticsService(@ApplicationContext Context ctx) {
		return new AnalyticsService(ctx);
	}
}
