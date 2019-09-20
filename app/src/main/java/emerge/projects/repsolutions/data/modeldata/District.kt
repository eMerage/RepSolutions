package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class District (

    @SerializedName("status")
    var districtStatus: Boolean = false,

    @SerializedName("error")
    var districtNetworkError: NetworkError = NetworkError(),

    @SerializedName("districtList")
    var districtList: ArrayList<DistrictList> = ArrayList<DistrictList>()

)
{}