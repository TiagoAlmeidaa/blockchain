package com.tiago.blockchain.model.state

import com.tiago.blockchain.model.vo.ApiError
import com.tiago.blockchain.model.vo.MarketPrice

sealed class BlockChainState {
    object OnLoading : BlockChainState()
    data class OnMarketPriceReceived(val marketPrice: MarketPrice) : BlockChainState()
    data class OnMarketPriceFetchFailed(val exception: ApiError) : BlockChainState()
    object InvalidResponse : BlockChainState()
}
