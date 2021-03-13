package com.tiago.blockchain.model.vo

data class BlockChainResponse(
    val marketPrice: MarketPrice? = null,
    val error: ApiError? = null
)
