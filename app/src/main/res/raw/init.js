(function() {
const __addressHex = "%1$s";
const __rpcURL = "%2$s";
const __chainID = "%3$s";

function executeCallback (id, error, value) {
  GemjarWallet.executeCallback(id, error, value)
}

window.GemjarWallet.init(__rpcURL, {
  getAccounts: function (cb) { cb(null, [__addressHex]) },
  processTransaction: function (tx, cb){
    console.log('signing a transaction', tx)
    const { id = 8888 } = tx
    GemjarWallet.addCallback(id, cb)

    var gasLimit = tx.gasLimit || tx.gas || null;
    var gasPrice = tx.gasPrice || null;
    var data = tx.data || null;
    var nonce = tx.nonce || -1;
    gem.signTransaction(id, tx.to || null, tx.value, nonce, gasLimit, gasPrice, data);
  },
  signMessage: function (msgParams, cb) {
      console.log('signMessage', msgParams)
      const { data, chainType } = msgParams
      const { id = 8888 } = msgParams
    GemjarWallet.addCallback(id, cb)
    gem.signMessage(id, data);
  },
  signPersonalMessage: function (msgParams, cb) {
      console.log('signPersonalMessage', msgParams)
      const { data, chainType } = msgParams
      const { id = 8888 } = msgParams
    GemjarWallet.addCallback(id, cb)
    gem.signPersonalMessage(id, data);
  },
  signTypedMessage: function (msgParams, cb) {
    console.log('signTypedMessage ', msgParams)
    const { data } = msgParams
    const { id = 8888 } = msgParams
    GemjarWallet.addCallback(id, cb)
    gem.signTypedMessage(id, JSON.stringify(msgParams))
  },
  ethCall: function (msgParams, cb) {
    console.log("eth_call", msgParams)
    const data = msgParams
    const { id = Math.floor((Math.random() * 100000) + 1) } = msgParams
    GemjarWallet.addCallback(id, cb)
    gem.ethCall(id, JSON.stringify(msgParams));
    //gem.ethCall(id, msgParams.to, msgParams.data, msgParams.value);
  },
  walletAddEthereumChain: function (msgParams, cb) {
    const data = msgParams
    const { id = Math.floor((Math.random() * 100000) + 1) } = msgParams
    console.log("walletAddEthereumChain", msgParams)
    GemjarWallet.addCallback(id, cb)
    gem.walletAddEthereumChain(id, JSON.stringify(msgParams));
    //webkit.messageHandlers.walletAddEthereumChain.postMessage({"name": "walletAddEthereumChain", "object": data, id: id})
  },
  walletSwitchEthereumChain: function (msgParams, cb) {
    const data = msgParams
    const { id = Math.floor((Math.random() * 100000) + 1) } = msgParams
    console.log("walletSwitchEthereumChain", msgParams)
    GemjarWallet.addCallback(id, cb)
    gem.walletSwitchEthereumChain(id, JSON.stringify(msgParams));
    //webkit.messageHandlers.walletSwitchEthereumChain.postMessage({"name": "walletSwitchEthereumChain", "object": data, id: id})
  },
  requestAccounts: function(cb) {
      id = Math.floor((Math.random() * 100000) + 1)
      console.log("requestAccounts", id)
      GemjarWallet.addCallback(id, cb)
      gem.requestAccounts(id);
  },
  enable: function() {
      return new Promise(function(resolve, reject) {
          //send back the coinbase account as an array of one
          resolve([__addressHex])
      })
  }
}, {
    address: __addressHex,
    networkVersion: __chainID
    //networkVersion: "0x" + parseInt(__chainID).toString(16) || null
})

window.web3.setProvider = function () {
  console.debug('Gemjar Wallet - overrode web3.setProvider')
}

window.web3.version.getNetwork = function(cb) {
    cb(null, __chainID)
}
window.web3.eth.getCoinbase = function(cb) {
    return cb(null, __addressHex)
}
window.web3.eth.defaultAccount = __addressHex

window.ethereum = web3.currentProvider
})();