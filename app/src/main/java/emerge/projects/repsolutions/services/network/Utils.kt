package emerge.projects.repsolutions.services.network

import android.content.Context
import android.net.ConnectivityManager

object Utils {

    fun checkInternetConnection(context: Context): Boolean {
        var isConnexted = false
        // get Connectivity Manager object to check connection
        val connect = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connect.activeNetworkInfo
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                isConnexted = true
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                isConnexted = true
            }
        } else {
            // not connected to the internet
            isConnexted = false
        }
        return isConnexted
    }


}