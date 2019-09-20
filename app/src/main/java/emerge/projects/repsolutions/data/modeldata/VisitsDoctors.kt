package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class VisitsDoctors(

    @SerializedName("status")
    var visitsDoctorsStatus: Boolean = false,

    @SerializedName("error")
    var networkError: NetworkError = NetworkError(),

    @SerializedName("VisitsDoctorsList")
    var visitsDoctorsList: ArrayList<VisitsDoctorsList> = ArrayList<VisitsDoctorsList>()


) {
}