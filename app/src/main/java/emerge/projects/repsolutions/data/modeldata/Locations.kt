package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class Locations(

    @SerializedName("status")
    var locationsStatus: Boolean = false,


    @SerializedName("locationsDuplicate")
    var isLocationsDuplicate: Boolean = false,

    @SerializedName("error")
    var locationsNetworkError: NetworkError = NetworkError(),




    @SerializedName("locationsList")
    var locationsList: ArrayList<LocationsList> = ArrayList<LocationsList>()

){}