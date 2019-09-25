package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class DoctorList(


    @SerializedName("id")
    var doctorID: Int = 0,

    @SerializedName("name")
    var doctorName: String = "",

    @SerializedName("imageUrl")
    var doctorImageUrl: String = "",

    @SerializedName("code")
    var doctorCode: String = "",

    @SerializedName("contactNo")
    var doctorContactNo: String = "",

    @SerializedName("registrationNo")
    var doctorRegistrationNo: String = "",


    @SerializedName("qualification")
    var doctorQualification: String = "",

    @SerializedName("createdDate")
    var doctorCreatedDate: String = "",

    @SerializedName("createdByID")
    var doctorCreatedByID: String = "",

    @SerializedName("createdByName")
    var doctorCreatedByName: String = "",



    @SerializedName("isApproved")
    var doctorIsApproved: Boolean = false,

    @SerializedName("isRejected")
    var doctorIsRejected: Boolean = false,

    @SerializedName("specializationList")
    var specializationList: ArrayList<SpecializationList> = ArrayList<SpecializationList>(),

    @SerializedName("locationList")
    var locationList: ArrayList<LocationsList> = ArrayList<LocationsList>(),
    @SerializedName("visitsProduct")
    var visitsProduct: ArrayList<ProductList> = ArrayList<ProductList>(),
    var allSpecializations: String = "",
    var allLocations: String = "",
    var doctorStats: String = "",

    var isSelect: Boolean = false

) {}