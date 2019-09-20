package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class Town (

    @SerializedName("status")
    var townStatus: Boolean = false,

    @SerializedName("error")
    var townNetworkError: NetworkError = NetworkError(),

    @SerializedName("townList")
    var townList: ArrayList<TownList> = ArrayList<TownList>()


)
{}