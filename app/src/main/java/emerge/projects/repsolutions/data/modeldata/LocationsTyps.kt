package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class LocationsTyps(

    @SerializedName("status")
    var locationsTypeStatus: Boolean = false,

    @SerializedName("error")
    var locationsTypeNetworkError: NetworkError = NetworkError(),

    @SerializedName("locationsTypeList")
    var locationsTypeList: ArrayList<LocationsTypeList> = ArrayList<LocationsTypeList>()

){}