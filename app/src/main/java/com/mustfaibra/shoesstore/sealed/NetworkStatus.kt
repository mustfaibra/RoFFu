package com.mustfaibra.shoesstore.sealed


sealed class NetworkStatus {
    object Connected : NetworkStatus()
    object Disconnected : NetworkStatus()
}
