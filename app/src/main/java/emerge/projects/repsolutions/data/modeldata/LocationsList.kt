package emerge.projects.repsolutions.data.modeldata

import com.google.gson.annotations.SerializedName

data class LocationsList(

    @SerializedName("id")
    var locationsID: Int = 0,

    @SerializedName("name")
    var locationsName: String = "",


    @SerializedName("latitude")
    var locationsLatitude: Double = 0.0,


    @SerializedName("longitude")
    var locationsLongitude: Double = 0.0,


    @SerializedName("address")
    var locationsAddress: String = "",


    @SerializedName("area")
    var locationsArea: String = "",

    @SerializedName("town")
    var locationsTown: String = "",

    @SerializedName("districtID")
    var locationsDistrictID: Int = 0,

    @SerializedName("district")
    var locationsDistrict: String = "",



    @SerializedName("createdByID")
    var locationsCreatedByID: Int = 0,


    @SerializedName("createdByName")
    var locationsCreatedByName: String = "",

    @SerializedName("locationTypeID")
    var locationsTypeID: Int = 0,


    @SerializedName("imageUrl")
    var locationsImageUrl: String = "",


    @SerializedName("locationType")
    var locationsType: String = "",

    @SerializedName("isApproved")
    var locationsIsApproved: Boolean = false,

    @SerializedName("isRejected")
    var locationsIsRejectedd: Boolean = false,

    var isSelect: Boolean = false



) {}