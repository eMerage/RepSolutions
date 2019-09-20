package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class VisitsDoctorsList(

    @SerializedName("id")
    var userID: Int = 0,

    @SerializedName("code")
    var code: String = "",

    @SerializedName("visitNumber")
    var visitNumber: String = "",


    @SerializedName("locationID")
    var locationID: Int = 0,

    @SerializedName("location")
    var location: String = "",

    @SerializedName("doctorID")
    var doctorID: Int = 0,

    @SerializedName("doctorName")
    var doctorName: String = "",

    @SerializedName("repID")
    var repID: Int = 0,

    @SerializedName("repName")
    var repName: String = "",

    @SerializedName("comment")
    var comment: String = "",

    @SerializedName("visitDate")
    var visitDate: String = "",

    @SerializedName("latitude")
    var latitude: Double = 0.0,

    @SerializedName("longitude")
    var longitude: Double = 0.0,

    @SerializedName("visitsProduct")
    var visitsProduct: ArrayList<ProductList> = ArrayList<ProductList>(),

    @SerializedName("imageUrl")
    var imageUrl: String = ""

) {
}