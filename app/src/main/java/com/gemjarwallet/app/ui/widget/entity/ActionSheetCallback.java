package com.gemjarwallet.app.ui.widget.entity;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;

import com.gemjarwallet.app.entity.SignAuthenticationCallback;
import com.gemjarwallet.app.entity.tokens.Token;
import com.gemjarwallet.app.web3.entity.Web3Transaction;

/**
 * Created by JB on 27/11/2020.
 */
public interface ActionSheetCallback
{
    void getAuthorisation(SignAuthenticationCallback callback);
    void sendTransaction(Web3Transaction tx);
    void dismissed(String txHash, long callbackId, boolean actionCompleted);
    void notifyConfirm(String mode);
    ActivityResultLauncher<Intent> gasSelectLauncher();
    default void signTransaction(Web3Transaction tx) { } // only WalletConnect uses this so far

    default void buttonClick(long callbackId, Token baseToken) { }; //for message only actionsheet

    default void notifyWalletConnectApproval(long chainId) { };    // used by WalletConnectRequest
    default void denyWalletConnect() { };
    default void openChainSelection() { };      // used by WalletConnectRequest
    default void buttonClick(String action, int Id) { };    // for passing
}
