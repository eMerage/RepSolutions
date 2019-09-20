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


class LocationRepo(application: Application) {

    var app: Application = application
    var apiInterface: APIInterface = ApiClient.client(application)

    val sharedPref = app?.getSharedPreferences("filename1", Context.MODE_PRIVATE)
    var networkErrorHandler: NetworkErrorHandler = NetworkErrorHandler()


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

        testList.add(LocationsList(1, "D1", 6.9866772, 79.8893072))
        testList.add(LocationsList(2, "D2", 6.9866772, 79.8893072))
        testList.add(LocationsList(3, "D3", 6.9866772, 79.8893072))

        testList.add(LocationsList(4, "D4", 6.9866772, 79.8893072))
        testList.add(LocationsList(5, "D5", 6.9866772, 79.8893072))
        testList.add(LocationsList(6, "D6", 6.9866772, 79.8893072))



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


/*
    fun getDistrict(loding: ObservableField<Boolean>, roleAdapter: ArrayAdapter<String>): District {

        var result = District()
        var data = District()
        var rol = roleAdapter

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
        testList.add(DistrictList(1, "D1"))
        testList.add(DistrictList(1, "D2"))
        testList.add(DistrictList(1, "D4"))
        testList.add(DistrictList(1, "D5"))
        testList.add(DistrictList(1, "D9"))


        Handler().postDelayed(Runnable {
            test.districtList = testList

            result = test
            loding.set(false)



            var districtArryList = ArrayList<String>()
            for(item in result.districtList){
                districtArryList.add(item.districtName)
            }
            rol= ArrayAdapter(app, R.layout.list_autocomplete_products, R.id.lbl_name,districtArryList)


        }, 5000)


        */
/*  apiInterface.getDistricts()
              .subscribeOn(Schedulers.io())
              .doOnError { it }
              .doOnTerminate { }
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(object : Observer<District> {
                  override fun onSubscribe(d: Disposable) {
                  }
                  override fun onNext(log: District) {
                      data = log
                  }
                  override fun onError(e: Throwable) {
                      data.districtNetworkError = networkErrorHandler(e)
                      result.postValue(data)
                      loding.set(false)
                  }
                  override fun onComplete() {
                      result.postValue(data)
                      loding.set(false)
                  }
              })

  *//*



        return result
    }
*/


    fun getDistrict(loding: ObservableField<Boolean>): MutableLiveData<District> {

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
        testList.add(DistrictList(1, "D1"))
        testList.add(DistrictList(1, "D2"))
        testList.add(DistrictList(1, "D4"))
        testList.add(DistrictList(1, "D5"))
        testList.add(DistrictList(1, "D9"))


        test.districtList = testList

        result.postValue(test)
        loding.set(false)


/*  apiInterface.getDistricts()
            .subscribeOn(Schedulers.io())
            .doOnError { it }
            .doOnTerminate { }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<District> {
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(log: District) {
                    data = log
                }
                override fun onError(e: Throwable) {
                    data.districtNetworkError = networkErrorHandler(e)
                    result.postValue(data)
                    loding.set(false)
                }
                override fun onComplete() {
                    result.postValue(data)
                    loding.set(false)
                }
            })

*/



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


        /*        apiInterface.getTown(districtID)
                    .subscribeOn(Schedulers.io())
                    .doOnError { it }
                    .doOnTerminate { }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Town> {
                        override fun onSubscribe(d: Disposable) {
                        }
                        override fun onNext(log: Town) {
                            data = log
                        }
                        override fun onError(e: Throwable) {
                            data.townNetworkError = networkErrorHandler(e)
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
        } else if (locName.isNullOrEmpty()) {
            data.locationsNetworkError.errorCode = "EMPTY"
            data.locationsNetworkError.errorTitle = "Empty"
            data.locationsNetworkError.errorMessage = "Location Name is empty !"
            result.postValue(data)

        } else if (locAddress.isNullOrEmpty()) {

            data.locationsNetworkError.errorCode = "EMPTY"
            data.locationsNetworkError.errorTitle = "Empty"
            data.locationsNetworkError.errorMessage = "Location Address is empty !"
            result.postValue(data)

        } else if (locArea.isNullOrEmpty()) {
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


        }


        return result
    }


}