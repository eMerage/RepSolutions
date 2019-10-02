package emerge.projects.repsolutions.ui.location.mvvm

import android.app.Application
import android.content.Context
import android.os.Handler
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonObject
import emerge.projects.repsolutions.BuildConfig
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.*
import emerge.projects.repsolutions.services.api.APIInterface
import emerge.projects.repsolutions.services.api.ApiClient
import emerge.projects.repsolutions.services.network.NetworkErrorHandler
import emerge.projects.repsolutions.services.network.Utils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern


class LocationRepo(application: Application) {

    var app: Application = application
    var apiInterface: APIInterface = ApiClient.client(application)

    val sharedPref = app?.getSharedPreferences("filename1", Context.MODE_PRIVATE)
    var networkErrorHandler: NetworkErrorHandler = NetworkErrorHandler()


    val tokenID = BuildConfig.TOKEN_ID

    private val USER_ID = "userID"
    private val USER_TYPE = "userType"
    private val USER_NAME = "userName"
    private val USER_EMAIL = "userEmail"
    private val USER_REMEMBER = "userRemember"


    fun getLocations(loding: ObservableField<Boolean>): MutableLiveData<Locations> {
        val result = MutableLiveData<Locations>()
        var data = Locations()
        loding.set(true)

        if (!Utils.checkInternetConnection(app))
            Toast.makeText(
                app,
                "No internet connection you will miss the latest information ",
                Toast.LENGTH_LONG
            ).show()


        val userId = sharedPref.getInt(USER_ID, 0)


        var test = Locations()

        test.locationsStatus = true


        var testList = ArrayList<LocationsList>()

        testList.add(LocationsList(1, "Asiri", 6.9866772, 79.8893072,
            "","Negombo","Negombo",
            2,"Gampaha",3,"rep",6,
            "","Ty",false,
            true,"Pending"))


        testList.add(LocationsList(2, "Nawaloka", 6.9866772, 79.8893072,
            "","Negombo","Negombo",
            2,"Gampaha",3,"rep",6,
            "","Ty",false,
            true,"Pending"))


        testList.add(LocationsList(3, "Hemas", 6.9866772, 79.8893072,
            "","Colombo","Kollupitiya",
            2,"Colombo",3,"rep",6,
            "","Hos",false,
            true,"Rejected"))


        testList.add(LocationsList(4, "Brownes", 6.9866772, 79.8893072,
            "","Negombo","Negombo",
            2,"Gampaha",3,"saman",6,
            "","Ty",false,
            true,"Approved"))


        test.locationsList = testList

        result.postValue(test)
        loding.set(false)


        /* apiInterface.getAllLocations(userId)
             .subscribeOn(Schedulers.io())
             .doOnError { it }
             .doOnTerminate { }
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(object : Observer<Locations> {
                 override fun onSubscribe(d: Disposable) {
                 }
                 override fun onNext(log: Locations) {
                     data = log
                 }
                 override fun onError(e: Throwable) {
                     data.locationsNetworkError = networkErrorHandler(e)
                     result.postValue(data)
                     loding.set(false)
                 }
                 override fun onComplete() {
                     result.postValue(data)
                     loding.set(false)
                 }
             })*/



        return result
    }


    fun getDistrictList(loding: ObservableField<Boolean>): MutableLiveData<District> {
        val result = MutableLiveData<District>()
        var data = District()
        loding.set(true)
        if (!Utils.checkInternetConnection(app))
            Toast.makeText(
                app,
                "No internet connection you will miss the latest information ",
                Toast.LENGTH_LONG
            ).show()





        var test = District()
        test.districtStatus = true

        var testList = ArrayList<DistrictList>()
        testList.add(DistrictList(1, "Ampara"))
        testList.add(DistrictList(2, "Anuradhapura"))
        testList.add(DistrictList(3, "Badulla"))
        testList.add(DistrictList(4, "Batticaloa"))
        testList.add(DistrictList(5, "Colombo"))


        testList.add(DistrictList(6, "Galle"))
        testList.add(DistrictList(7, "Gampaha"))
        testList.add(DistrictList(8, "Hambantota"))


        test.districtList = testList

        result.postValue(test)
        loding.set(false)





 /*       apiInterface.getDistricts(tokenID)
            .subscribeOn(Schedulers.io())
            .doOnError { it }
            .doOnTerminate { }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<District> {
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(log: District) {
                    data = log
                }override fun onError(e: Throwable) {
                    data.districtNetworkError = networkErrorHandler(e)

                    result.postValue(data)
                    loding.set(false)
                }
                override fun onComplete() {
                    result.postValue(data)
                    loding.set(false)
                }
            })*/
        return result
    }


    fun getTown(loding: ObservableField<Boolean>, districtID: Int): MutableLiveData<Town> {
        val result = MutableLiveData<Town>()
        var data = Town()

        loding.set(true)

        if (!Utils.checkInternetConnection(app))
            Toast.makeText(
                app,
                "No internet connection you will miss the latest information ",
                Toast.LENGTH_LONG
            ).show()


        var test = Town()
        test.townStatus = true

        var testList = ArrayList<TownList>()
        testList.add(TownList(1, "T1"))
        testList.add(TownList(1, "T2"))
        testList.add(TownList(1, "T4"))
        testList.add(TownList(1, "T5"))
        testList.add(TownList(1, "T9"))


        test.townList = testList

        result.postValue(test)
        loding.set(false)



        return result
    }

    fun getArea(loding: ObservableField<Boolean>): MutableLiveData<Area> {
        val result = MutableLiveData<Area>()
        var data = Area()

        loding.set(true)

        if (!Utils.checkInternetConnection(app))
            Toast.makeText(
                app,
                "No internet connection you will miss the latest information ",
                Toast.LENGTH_LONG
            ).show()


        var test = Area()
        test.areaStatus = true

        var testList = ArrayList<AreaList>()
        testList.add(AreaList(1, "A1"))
        testList.add(AreaList(1, "A2"))
        testList.add(AreaList(1, "A4"))
        testList.add(AreaList(1, "A5"))
        testList.add(AreaList(1, "A9"))


        test.areaList = testList

        result.postValue(test)
        loding.set(false)



        return result
    }


    fun getLocationType(loding: ObservableField<Boolean>): MutableLiveData<LocationsTyps> {
        val result = MutableLiveData<LocationsTyps>()
        var data = LocationsTyps()

        loding.set(true)

        if (!Utils.checkInternetConnection(app))
            Toast.makeText(
                app,
                "No internet connection you will miss the latest information ",
                Toast.LENGTH_LONG
            ).show()


        var test = LocationsTyps()
        test.locationsTypeStatus = true

        var testList = ArrayList<LocationsTypeList>()
        testList.add(LocationsTypeList(1, "LT1"))
        testList.add(LocationsTypeList(2, "LT2"))
        testList.add(LocationsTypeList(5, "LT4"))
        testList.add(LocationsTypeList(9, "LT5"))
        testList.add(LocationsTypeList(47, "LT9"))


        // if no type add "No Location Type"

        test.locationsTypeList = testList

        result.postValue(test)
        loding.set(false)



        return result
    }

    fun searchLocation(searchName : String,locationList : ArrayList<LocationsList>): MutableLiveData<ArrayList<LocationsList>> {

        var result = MutableLiveData<ArrayList<LocationsList>>()
        var data = ArrayList<LocationsList>()


        var patternName = searchName

        if((searchName.isNullOrEmpty() ) ||  (searchName == "all")  ||  (searchName == "") ){
            result.postValue(locationList)
        }else{
            for (loc in locationList) {
                var listLocName = loc.locationsName

                var pattern = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                var matcher = pattern.matcher(listLocName)
                if (matcher.lookingAt()) {
                    data.add(loc)
                }

                var listLocArea = loc.locationsArea
                var patternArea = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                var matcherArea = patternArea.matcher(listLocArea)

                if (matcherArea.lookingAt()) {
                    data.add(loc)
                }

                var listLocTown = loc.locationsTown
                var patternTown = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                var matcherTown = patternTown.matcher(listLocTown)

                if (matcherTown.lookingAt()) {
                    data.add(loc)
                }


                var listLocDistrict = loc.locationsDistrict
                var patternDistrict = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                var matcherDistrict = patternDistrict.matcher(listLocDistrict)

                if (matcherDistrict.lookingAt()) {
                    data.add(loc)
                }

                var listLocType = loc.locationsType
                var patternType = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                var matcherType = patternType.matcher(listLocType)

                if (matcherType.lookingAt()) {
                    data.add(loc)
                }

                var listLocRep = loc.locationsCreatedByName
                var patternRep = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                var matcherRep = patternRep.matcher(listLocRep)

                if (matcherRep.lookingAt()) {
                    data.add(loc)
                }


                var listLocStatus = loc.locationsApprovalStats
                var patternStatus = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                var matcherStatus = patternStatus.matcher(listLocStatus)

                if (matcherStatus.lookingAt()) {
                    data.add(loc)
                }

            }

            data.distinctBy { it.locationsID }
            result.postValue(data)

        }


        return result
    }


    fun saveNewLocation(
        loding: ObservableField<Boolean>,
        currentLocation: LatLng,
        locName: String,
        locAddress: String,
        locArea: String,
        locTownID: Int,
        locDistrictID: Int,
        locTypeId: Int,
        locationDuplicateStatus: Boolean

    ): MutableLiveData<Locations> {


        val result = MutableLiveData<Locations>()
        var data = Locations()


        if (!Utils.checkInternetConnection(app)) {
            data.locationsNetworkError.errorCode = "INT"
            data.locationsNetworkError.errorTitle = "Connection"
            data.locationsNetworkError.errorMessage = "No internet connection !"

            result.postValue(data)
        } else if (currentLocation == null) {
            data.locationsNetworkError.errorCode = "LOC"
            data.locationsNetworkError.errorTitle = "Location"
            data.locationsNetworkError.errorMessage = "Location not set properly,please try again !"

            result.postValue(data)

        } else if ((locName.isNullOrEmpty()) || (locName == "null")) {
            data.locationsNetworkError.errorCode = "EMPTY"
            data.locationsNetworkError.errorTitle = "Empty"
            data.locationsNetworkError.errorMessage = "Location Name is empty !"
            result.postValue(data)

        } else if (locAddress.isNullOrEmpty() || (locAddress == "null")) {

            data.locationsNetworkError.errorCode = "EMPTY"
            data.locationsNetworkError.errorTitle = "Empty"
            data.locationsNetworkError.errorMessage = "Location Address is empty !"
            result.postValue(data)

        } else if ((locArea.isNullOrEmpty()) || (locArea == "null")) {
            data.locationsNetworkError.errorCode = "EMPTY"
            data.locationsNetworkError.errorTitle = "Empty"
            data.locationsNetworkError.errorMessage = "Location Area is empty !"
            result.postValue(data)

        } else if ((locTownID == null) || (locTownID == 0)) {

            data.locationsNetworkError.errorCode = "EMPTY"
            data.locationsNetworkError.errorTitle = "Empty"
            data.locationsNetworkError.errorMessage = "Location town is empty !"
            result.postValue(data)

        } else if ((locDistrictID == null) || (locDistrictID == 0)) {
            data.locationsNetworkError.errorCode = "EMPTY"
            data.locationsNetworkError.errorTitle = "Empty"
            data.locationsNetworkError.errorMessage = "Please select the District !"
            result.postValue(data)

        } else {

            loding.set(true)
            val userId = sharedPref.getInt(USER_ID, 0)

            val jsonObject = JsonObject()

            jsonObject.addProperty("name", locName)
            jsonObject.addProperty("LocationTypeID", locTypeId)
            jsonObject.addProperty("Address", locAddress)
            jsonObject.addProperty("Town", locTownID)
            jsonObject.addProperty("DistrictID", locDistrictID)
            jsonObject.addProperty("Area", locArea)
            jsonObject.addProperty("Latitude", currentLocation.latitude)
            jsonObject.addProperty("Longitude", currentLocation.longitude)
            jsonObject.addProperty("CreatedByID", userId)
            jsonObject.addProperty("IsAfterSuggestion", locationDuplicateStatus)




            loding.set(false)



            var test = Locations()

            test.locationsStatus = false
            test.isLocationsDuplicate = true


            var testList = ArrayList<LocationsList>()

            testList.add(LocationsList(1, "D1", 6.9866772, 79.8893072))
            testList.add(LocationsList(2, "D2", 6.9866772, 79.8893072))
            testList.add(LocationsList(3, "D3", 6.9866772, 79.8893072))

            testList.add(LocationsList(4, "D4", 6.9866772, 79.8893072))
            testList.add(LocationsList(5, "D5", 6.9866772, 79.8893072))
            testList.add(LocationsList(6, "D6", 6.9866772, 79.8893072))



            test.locationsList = testList


            result.postValue(test)


/*
            apiInterface.saveLocation(jsonObject)
                .subscribeOn(Schedulers.io())
                .doOnError { it }
                .doOnTerminate { }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Locations> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(log: Locations) {
                        data = log
                    }

                    override fun onError(e: Throwable) {
                        loding.set(false)
                        data.locationsNetworkError = networkErrorHandler(e)
                        result.postValue(data)

                    }

                    override fun onComplete() {
                        loding.set(true)
                        result.postValue(data)
                    }
                })
*/


        }


        return result
    }


}