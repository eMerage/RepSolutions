package emerge.projects.repsolutions.services.api

import com.google.gson.JsonObject
import emerge.projects.repsolutions.data.modeldata.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList


interface APIInterface {


    @GET("User/ValidateUser")
    abstract fun validateUser(@Query("username") username: String, @Query("password") password: String, @Query("pushtokenid") pushtokenid: String): Observable<User>


    @GET("Visit/GetAllVisitsByAdmin")
    abstract fun getAllVisits(@Query("useriD") useriD: Int): Observable<VisitsDoctors>


    @GET("Doctor/GetApprovedDoctorsNearLocation")
    abstract fun getApprovedDoctors(@Query("MRID") mrID: Int, @Query("Latitude") latitude: Double, @Query("Longitude") longitude: Double): Observable<Doctor>

    @GET("Location/GetNearbyLocationsOfDoctor")
    abstract fun getDoctorsLocations(@Query("DoctorID") docID: Int, @Query("Latitude") latitude: Double, @Query("Longitude") longitude: Double): Observable<Locations>


    @GET("Product/GetAllProductsForMR")
    abstract fun getAllProductsForUser(@Query("MRID") mrID: Int): Observable<Product>


    @GET("Product/GetSampleProductsForMR")
    abstract fun getSampleProductsForUser(@Query("MRID") mrID: Int): Observable<Product>


    @GET("Location/GetAllLocationsByMR")
    abstract fun getAllLocations(@Query("MRID") mrID: Int): Observable<Locations>


    @POST("Location/SaveLocation")
    abstract fun saveLocation(@Body locationInfo: JsonObject): Observable<Locations>


    @GET("District/GetAllDistricts")
    abstract fun getDistricts(@Query("tokenID") tokenID: String): Observable<District>


    @GET("Town/GetAllTown")
    abstract fun getTown(@Query("DistrictID") mrID: Int): Observable<Town>


    @GET("Doctor/GetAllDoctors")
    abstract fun getDoctors(@Query("MRID") mrID: Int): Observable<Doctor>


}