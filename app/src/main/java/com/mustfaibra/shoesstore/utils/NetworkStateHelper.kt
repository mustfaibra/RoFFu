package com.mustfaibra.shoesstore.utils


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import com.mustfaibra.shoesstore.sealed.NetworkStatus

/**
 * A helper class that make use of LiveData to monitor network status and keep track of when it's Connected & Disconnected
 * It take the context to get system services that we need like ConnectivityService
 */
class NetworkHelper(cxt: Context) : LiveData<NetworkStatus>() {
    /** The list of the current valid connections */
    val validConnections: MutableSet<Network> = HashSet()

    /** Our connectivity manager */
    var connectivityManager =
        cxt.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /** The callback that we are going to use later */
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    /** This function is called when there are someone observing this LiveData */
    override fun onActive() {
        super.onActive()
        connectivityManagerCallback = getConnectivityManagerCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)
    }

    /** Called when all observers is removed , which is unregister the callback that we made in order to keep the space efficiency */
    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }

    /** A function that is responsible of hoisting/posting the current network status for all observers */
    fun hoistStatus() {
        postValue(
            if (validConnections.isEmpty()) NetworkStatus.Disconnected
            else NetworkStatus.Connected
        )
    }

    /** Getting our Connectivity callbacks when some observers exists */
    private fun getConnectivityManagerCallback() = object : ConnectivityManager.NetworkCallback() {
        /** when there exist a network available , if it has a network connection , then add it to our valid connections and tell the observers */
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            val hasConnections =
                capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
            if (hasConnections) {
                validConnections.add(network)
                hoistStatus()
            }
        }

        /** when we lose a network , then remove it from our valid connections and also tell the observers */
        override fun onLost(network: Network) {
            super.onLost(network)
            validConnections.remove(network)
            hoistStatus()
        }

        /**
         * When some network that is already exist change , check it again for internet connection
         * And yeah , don't forget to tell our dear observers , again ! :-D
         */
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities,
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                validConnections.add(network)
            } else {
                validConnections.remove(network)
            }
            hoistStatus()
        }
    }

}