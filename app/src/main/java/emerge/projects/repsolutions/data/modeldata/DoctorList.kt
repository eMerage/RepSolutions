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
    var doctorIsRejectedd: Boolean = false,

    @SerializedName("specializationList")
    var specializationList: ArrayList<SpecializationList> = ArrayList<SpecializationList>(),


    @SerializedName("visitsProduct")
    var visitsProduct: ArrayList<ProductList> = ArrayList<ProductList>(),


    var isSelect: Boolean = false

) {}