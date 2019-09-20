package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class Specialization(

    @SerializedName("status")
    var specStatus: Boolean = false,

    @SerializedName("error")
    var specNetworkError: NetworkError = NetworkError(),

    @SerializedName("SpecializationList")
    var specializationList: ArrayList<SpecializationList> = ArrayList<SpecializationList>()


)
{}