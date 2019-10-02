package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class Doctor(

    @SerializedName("status")
    var doctorsStatus: Boolean = false,

    @SerializedName("error")
    var networkError: NetworkError = NetworkError(),

    @SerializedName("doctorsDuplicate")
    var isDoctorsDuplicate: Boolean = false,

    @SerializedName("approvedDoctorList")
    var approvedDoctorList: ArrayList<DoctorList> = ArrayList<DoctorList>()


)
{}