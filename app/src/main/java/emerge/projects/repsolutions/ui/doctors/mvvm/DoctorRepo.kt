package emerge.projects.repsolutions.ui.doctors.mvvm

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import emerge.projects.repsolutions.BuildConfig
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

class DoctorRepo(application: Application) {


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


    fun getDoctorsVisits(loding: ObservableField<Boolean>): MutableLiveData<VisitsDoctors> {
        val result = MutableLiveData<VisitsDoctors>()
        var data = VisitsDoctors()
        loding.set(true)

        if (!Utils.checkInternetConnection(app))
            Toast.makeText(
                app,
                "No internet connection you will miss the latest information ",
                Toast.LENGTH_LONG
            ).show()

        val userId = sharedPref.getInt(USER_ID, 0)

        apiInterface.getAllVisits(userId)
            .subscribeOn(Schedulers.io())
            .doOnError { it }
            .doOnTerminate { }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<VisitsDoctors> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(log: VisitsDoctors) {
                    data = log
                }

                override fun onError(e: Throwable) {
                    data.networkError = networkErrorHandler(e)
                    result.postValue(data)
                    loding.set(false)
                }

                override fun onComplete() {
                    result.postValue(data)
                    loding.set(false)
                }
            })
        return result
    }


    fun getApprovedDoctors(
        loding: ObservableField<Boolean>,
        lat: Double,
        log: Double
    ): MutableLiveData<Doctor> {
        val result = MutableLiveData<Doctor>()
        var data = Doctor()
        loding.set(true)


        if (!Utils.checkInternetConnection(app)) {
            Toast.makeText(app, "No internet connection !", Toast.LENGTH_LONG).show()
        } else {
            val userId = sharedPref.getInt(USER_ID, 0)


            var test = Doctor()

            test.doctorsStatus = true


            var testList = ArrayList<DoctorList>()

            testList.add(DoctorList(1, "D1"))
            testList.add(DoctorList(2, "D2"))
            testList.add(DoctorList(3, "D3"))



            test.approvedDoctorList = testList



            result.postValue(test)
            loding.set(false)


            /*   apiInterface.getApprovedDoctors(userId,lat,log)
                   .subscribeOn(Schedulers.io())
                   .doOnError { it }
                   .doOnTerminate { }
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(object : Observer<Doctor> {
                       override fun onSubscribe(d: Disposable) {
                       }
                       override fun onNext(log: Doctor) {
                           data = log
                       }
                       override fun onError(e: Throwable) {
                         //  data.networkError = networkErrorHandler(e)


                           //----------
                           var test = Doctor()

                           var testList = ArrayList<DoctorList>()

                           testList.add(DoctorList(1,"D1"))
                           testList.add(DoctorList(2,"D2"))
                           testList.add(DoctorList(3,"D3"))

                           test.doctorsStatus = true

                           test.approvedDoctorList = testList

                           //----------



                           result.postValue(data)
                           loding.set(false)
                       }
                       override fun onComplete() {
                           result.postValue(data)
                           loding.set(false)
                       }
                   })*/

        }



        return result
    }


    fun getDoctorsLocations(
        loding: ObservableField<Boolean>,
        docID: Int,
        lat: Double,
        log: Double
    ): MutableLiveData<Locations> {

        val result = MutableLiveData<Locations>()
        var data = Locations()
        loding.set(true)

        if (!Utils.checkInternetConnection(app)) {
            Toast.makeText(app, "No internet connection !", Toast.LENGTH_LONG).show()
        } else {


            var test = Locations()

            test.locationsStatus = true


            var testList = ArrayList<LocationsList>()

            testList.add(LocationsList(1, "L1"))
            testList.add(LocationsList(2, "L2"))
            testList.add(LocationsList(3, "L3"))



            test.locationsList = testList



            result.postValue(test)
            loding.set(false)


            /*  apiInterface.getDoctorsLocations(docID,lat,log)
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
                          data.networkError = networkErrorHandler(e)
                          result.postValue(data)
                          loding.set(false)
                      }
                      override fun onComplete() {
                          result.postValue(data)
                          loding.set(false)
                      }
                  })*/

        }

        return result
    }


    fun getUsersProducts(loding: ObservableField<Boolean>): MutableLiveData<Product> {
        val result = MutableLiveData<Product>()
        var data = Product()
        loding.set(true)

        if (!Utils.checkInternetConnection(app)) {
            Toast.makeText(app, "No internet connection !", Toast.LENGTH_LONG).show()
        } else {
            val userId = sharedPref.getInt(USER_ID, 0)


            var test = Product()

            test.productStatus = true


            var testList = ArrayList<ProductList>()

            testList.add(ProductList(1, "P1"))
            testList.add(ProductList(2, "P2"))
            testList.add(ProductList(3, "P3"))

            testList.add(ProductList(4, "P4"))
            testList.add(ProductList(5, "P5"))
            testList.add(ProductList(6, "P6"))

            testList.add(ProductList(7, "P7"))
            testList.add(ProductList(8, "P8"))
            testList.add(ProductList(9, "P9"))



            test.productList = testList



            result.postValue(test)
            loding.set(false)


            /* apiInterface.getAllProductsForUser(userId)
                 .subscribeOn(Schedulers.io())
                 .doOnError { it }
                 .doOnTerminate { }
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(object : Observer<Product> {
                     override fun onSubscribe(d: Disposable) {
                     }
                     override fun onNext(log: Product) {
                         data = log
                     }
                     override fun onError(e: Throwable) {
                         data.productNetworkError = networkErrorHandler(e)
                         result.postValue(data)
                         loding.set(false)
                     }
                     override fun onComplete() {
                         result.postValue(data)
                         loding.set(false)
                     }
                 })*/

        }

        return result
    }


    fun getSampleProducts(loding: ObservableField<Boolean>): MutableLiveData<Product> {
        val result = MutableLiveData<Product>()
        var data = Product()
        loding.set(true)

        if (!Utils.checkInternetConnection(app)) {
            Toast.makeText(app, "No internet connection !", Toast.LENGTH_LONG).show()
        } else {
            val userId = sharedPref.getInt(USER_ID, 0)


            var test = Product()

            test.productStatus = true


            var testList = ArrayList<ProductList>()

            testList.add(ProductList(1, "S1"))
            testList.add(ProductList(2, "S2"))
            testList.add(ProductList(3, "S3"))

            testList.add(ProductList(4, "S4"))
            testList.add(ProductList(5, "S5"))
            testList.add(ProductList(6, "S6"))

            testList.add(ProductList(7, "S7"))
            testList.add(ProductList(8, "S8"))
            testList.add(ProductList(9, "S9"))



            test.productList = testList



            result.postValue(test)
            loding.set(false)


            /* apiInterface.getSampleProductsForUser(userId)
                 .subscribeOn(Schedulers.io())
                 .doOnError { it }
                 .doOnTerminate { }
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(object : Observer<Product> {
                     override fun onSubscribe(d: Disposable) {
                     }
                     override fun onNext(log: Product) {
                         data = log
                     }
                     override fun onError(e: Throwable) {
                         data.productNetworkError = networkErrorHandler(e)
                         result.postValue(data)
                         loding.set(false)
                     }
                     override fun onComplete() {
                         result.postValue(data)
                         loding.set(false)
                     }
                 })*/

        }

        return result
    }


    fun getDoctors(loding: ObservableField<Boolean>): MutableLiveData<Doctor> {
        val result = MutableLiveData<Doctor>()
        var data = Doctor()

        loding.set(true)

        if (!Utils.checkInternetConnection(app))
            Toast.makeText(
                app,
                "No internet connection you will miss the latest information ",
                Toast.LENGTH_LONG
            ).show()

        val userId = sharedPref.getInt(USER_ID, 0)


        var test = Doctor()

        test.doctorsStatus = true


        var testList = ArrayList<DoctorList>()

        var testSpec1 = ArrayList<SpecializationList>()
        testSpec1.add(SpecializationList(1, "Pediatrician"))
        testSpec1.add(SpecializationList(2, "Surgeon"))



        testList.add(
            DoctorList(
                1,
                "Channd D Ranasinge",
                "",
                "CDR",
                "0716607483",
                "RDG558",
                "MBBS",
                "",
                "",
                "Saman",
                false,
                true,
                testSpec1,
                ArrayList<LocationsList>(),
                ArrayList<ProductList>(),
                "Pediatrician/Surgeon",
                "Asiri/Nawaloka",
                "Pending"
            )
        )


        var testSpec2 = ArrayList<SpecializationList>()
        testSpec2.add(SpecializationList(2, "Surgeon"))



        testList.add(
            DoctorList(
                2,
                "Lakshmen Obeysekara",
                "",
                "CDR",
                "0716607483",
                "RDG558",
                "MBBS",
                "",
                "",
                "Kamal",
                false,
                true,
                testSpec2,
                ArrayList<LocationsList>(),
                ArrayList<ProductList>(),
                "Surgeon",
                "Asiri/Nawaloka",
                "Pending"
            )
        )


        var testSpec3 = ArrayList<SpecializationList>()
        testSpec3.add(SpecializationList(3, "Oncologist"))

        testList.add(
            DoctorList(
                3,
                "Priyantha Madawa",
                "",
                "CDR",
                "0716607483",
                "RDG558",
                "MBBS",
                "",
                "",
                "Saman",
                false,
                true,
                testSpec3,
                ArrayList<LocationsList>(),
                ArrayList<ProductList>(),
                "Oncologist",
                "Asiri/Nawaloka",
                "Pending"
            )
        )


        var testSpec4 = ArrayList<SpecializationList>()
        testSpec4.add(SpecializationList(3, "Oncologist"))

        testList.add(
            DoctorList(
                4,
                "R.S. Jayatilake",
                "",
                "CDR",
                "0716607483",
                "RDG558",
                "MBBS",
                "",
                "",
                "Ruwan",
                false,
                true,
                testSpec4,
                ArrayList<LocationsList>(),
                ArrayList<ProductList>(),
                "Oncologist",
                "Asiri/Nawaloka",
                "Pending"
            )
        )


        var testSpec5 = ArrayList<SpecializationList>()
        testSpec5.add(SpecializationList(56, "Neurologist"))

        testList.add(
            DoctorList(
                5,
                "Panduka Jayasekara",
                "",
                "CDR",
                "0716607483",
                "RDG558",
                "MBBS",
                "",
                "",
                "Aman",
                false,
                true,
                testSpec5,
                ArrayList<LocationsList>(),
                ArrayList<ProductList>(),
                "Neurologist",
                "Asiri/Nawaloka",
                "Pending"
            )
        )


        testList.add(
            DoctorList(
                6,
                "Jayamini Horadugoda",
                "",
                "CDR",
                "0716607483",
                "RDG558",
                "MBBS",
                "",
                "",
                "Suman",
                false,
                true,
                ArrayList<SpecializationList>(),
                ArrayList<LocationsList>(),
                ArrayList<ProductList>(),
                "Embryologist",
                "Asiri/Nawaloka",
                "Pending"
            )
        )



        test.approvedDoctorList = testList

        result.postValue(test)
        loding.set(false)


        /*apiInterface.getDoctors(userId)
            .subscribeOn(Schedulers.io())
            .doOnError { it }
            .doOnTerminate { }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Doctor> {
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(log: Doctor) {
                    data = log
                }
                override fun onError(e: Throwable) {
                    data.networkError = networkErrorHandler(e)
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



    fun getApprovedDoctors(loding: ObservableField<Boolean>): MutableLiveData<Doctor> {
        val result = MutableLiveData<Doctor>()
        var data = Doctor()

        loding.set(true)

        if (!Utils.checkInternetConnection(app))
            Toast.makeText(
                app,
                "No internet connection you will miss the latest information ",
                Toast.LENGTH_LONG
            ).show()

        val userId = sharedPref.getInt(USER_ID, 0)


        var test = Doctor()

        test.doctorsStatus = true


        var testList = ArrayList<DoctorList>()

        var testSpec1 = ArrayList<SpecializationList>()
        testSpec1.add(SpecializationList(1, "Pediatrician"))
        testSpec1.add(SpecializationList(2, "Surgeon"))



        testList.add(
            DoctorList(
                1,
                "Channd D Ranasinge",
                "",
                "CDR",
                "0716607483",
                "RDG558",
                "MBBS",
                "",
                "",
                "Saman",
                false,
                true,
                testSpec1,
                ArrayList<LocationsList>(),
                ArrayList<ProductList>(),
                "Pediatrician/Surgeon",
                "Asiri/Nawaloka",
                "Pending"
            )
        )


        var testSpec2 = ArrayList<SpecializationList>()
        testSpec2.add(SpecializationList(2, "Surgeon"))



        testList.add(
            DoctorList(
                2,
                "Lakshmen Obeysekara",
                "",
                "CDR",
                "0716607483",
                "RDG558",
                "MBBS",
                "",
                "",
                "Kamal",
                false,
                true,
                testSpec2,
                ArrayList<LocationsList>(),
                ArrayList<ProductList>(),
                "Surgeon",
                "Asiri/Nawaloka",
                "Pending"
            )
        )


        var testSpec3 = ArrayList<SpecializationList>()
        testSpec3.add(SpecializationList(3, "Oncologist"))

        testList.add(
            DoctorList(
                3,
                "Priyantha Madawa",
                "",
                "CDR",
                "0716607483",
                "RDG558",
                "MBBS",
                "",
                "",
                "Saman",
                false,
                true,
                testSpec3,
                ArrayList<LocationsList>(),
                ArrayList<ProductList>(),
                "Oncologist",
                "Asiri/Nawaloka",
                "Pending"
            )
        )


        var testSpec4 = ArrayList<SpecializationList>()
        testSpec4.add(SpecializationList(3, "Oncologist"))

        testList.add(
            DoctorList(
                4,
                "R.S. Jayatilake",
                "",
                "CDR",
                "0716607483",
                "RDG558",
                "MBBS",
                "",
                "",
                "Ruwan",
                false,
                true,
                testSpec4,
                ArrayList<LocationsList>(),
                ArrayList<ProductList>(),
                "Oncologist",
                "Asiri/Nawaloka",
                "Pending"
            )
        )


        var testSpec5 = ArrayList<SpecializationList>()
        testSpec5.add(SpecializationList(56, "Neurologist"))

        testList.add(
            DoctorList(
                5,
                "Panduka Jayasekara",
                "",
                "CDR",
                "0716607483",
                "RDG558",
                "MBBS",
                "",
                "",
                "Aman",
                false,
                true,
                testSpec5,
                ArrayList<LocationsList>(),
                ArrayList<ProductList>(),
                "Neurologist",
                "Asiri/Nawaloka",
                "Pending"
            )
        )


        testList.add(
            DoctorList(
                6,
                "Jayamini Horadugoda",
                "",
                "CDR",
                "0716607483",
                "RDG558",
                "MBBS",
                "",
                "",
                "Suman",
                false,
                true,
                ArrayList<SpecializationList>(),
                ArrayList<LocationsList>(),
                ArrayList<ProductList>(),
                "Embryologist",
                "Asiri/Nawaloka",
                "Pending"
            )
        )



        test.approvedDoctorList = testList

        result.postValue(test)
        loding.set(false)


        /*apiInterface.getDoctors(userId)
            .subscribeOn(Schedulers.io())
            .doOnError { it }
            .doOnTerminate { }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Doctor> {
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(log: Doctor) {
                    data = log
                }
                override fun onError(e: Throwable) {
                    data.networkError = networkErrorHandler(e)
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








    fun searchDoctors(
        searchName: String,
        doctorsList: ArrayList<DoctorList>
    ): MutableLiveData<ArrayList<DoctorList>> {

        var result = MutableLiveData<ArrayList<DoctorList>>()
        var data = ArrayList<DoctorList>()

        var patternName = searchName

        if ((searchName.isNullOrEmpty()) || (searchName == "all") || (searchName == "")) {
            result.postValue(doctorsList)
        } else {
            for (doc in doctorsList) {
                var listDocName = doc.doctorName
                var pattern = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                var matcher = pattern.matcher(listDocName)
                if (matcher.lookingAt()) {
                    data.add(doc)
                }


                var listDocCode = doc.doctorCode
                var patternCode = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                var matcherCode = patternCode.matcher(listDocCode)

                if (matcherCode.lookingAt()) {
                    data.add(doc)
                }


                var listContactNumber = doc.doctorContactNo
                var patternContactNumber = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                var matcherContactNumber = patternContactNumber.matcher(listContactNumber)

                if (matcherContactNumber.lookingAt()) {
                    data.add(doc)
                }

                var listRegNumber = doc.doctorRegistrationNo
                var patternRegNumber = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                var matcherRegNumber = patternRegNumber.matcher(listRegNumber)

                if (matcherRegNumber.lookingAt()) {
                    data.add(doc)
                }


                var listQualification = doc.doctorQualification
                var patternQualification = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                var matcherQualification = patternQualification.matcher(listQualification)
                if (matcherQualification.lookingAt()) {
                    data.add(doc)
                }


                var listDocRep = doc.doctorCreatedByName
                var patternRep = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                var matcherRep = patternRep.matcher(listDocRep)

                if (matcherRep.lookingAt()) {
                    data.add(doc)
                }


                for (docSpec in doc.specializationList) {

                    var listDocSpec = docSpec.specName
                    var patternSpec = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                    var matcherSpec = patternSpec.matcher(listDocSpec)

                    if (matcherSpec.lookingAt()) {
                        data.add(doc)
                    }

                }

                for (docLoc in doc.locationList) {

                    var listDocLoc = docLoc.locationsName
                    var patternLoc = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                    var matcherLoc = patternLoc.matcher(listDocLoc)
                    if (matcherLoc.lookingAt()) {
                        data.add(doc)
                    }

                }

                var listDocStatus = doc.doctorStats
                var patternStatus = Pattern.compile(patternName, Pattern.CASE_INSENSITIVE)
                var matcherStatus = patternStatus.matcher(listDocStatus)

                if (matcherStatus.lookingAt()) {
                    data.add(doc)
                }

            }

            data.distinctBy { it.doctorID }
            result.postValue(data)
        }

        return result
    }


    fun getDoctorsSpecialization(loding: ObservableField<Boolean>): MutableLiveData<Specialization> {
        val result = MutableLiveData<Specialization>()
        var data = Specialization()

        loding.set(true)

        if (!Utils.checkInternetConnection(app))
            Toast.makeText(
                app,
                "No internet connection you will miss the latest information ",
                Toast.LENGTH_LONG
            ).show()


        var test = Specialization()

        test.specStatus = true


        var testList = ArrayList<SpecializationList>()

        testList.add(SpecializationList(1, "Surgeon"))
        testList.add(SpecializationList(2, "Oncologist"))
        testList.add(SpecializationList(3, "Neurologist"))
        testList.add(SpecializationList(4, "Cardiologists"))
        testList.add(SpecializationList(5, "Dermatologists"))
        testList.add(SpecializationList(6, "Gastroenterologists"))

        testList.add(SpecializationList(7, "Pathologists"))
        testList.add(SpecializationList(8, "Radiologists"))
        testList.add(SpecializationList(9, "Urologists"))


        test.specializationList = testList

        testList.sortBy { it.specName }

        result.postValue(test)
        loding.set(false)


        /*   apiInterface.getASpecializations(tokenID)
               .subscribeOn(Schedulers.io())
               .doOnError { it }
               .doOnTerminate { }
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(object : Observer<Specialization> {
                   override fun onSubscribe(d: Disposable) {
                   }
                   override fun onNext(log: Specialization) {
                       data = log
                   }
                   override fun onError(e: Throwable) {
                       data.specNetworkError = networkErrorHandler(e)
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


    fun saveNewDoctor(
        loding: ObservableField<Boolean>,
        docName: String,
        docTpNumber: String,
        docRegNumber: String,
        docQualification: String,
        docSpecList: ArrayList<SpecializationList>,
        docDuplicateStatus: Boolean

    ): MutableLiveData<Doctor> {


        val result = MutableLiveData<Doctor>()
        var data = Doctor()


        if (!Utils.checkInternetConnection(app)) {
            data.networkError.errorCode = "INT"
            data.networkError.errorTitle = "Connection"
            data.networkError.errorMessage = "No internet connection !"

            result.postValue(data)
        } else if ((docName.isNullOrEmpty()) || (docName == "null")) {
            data.networkError.errorCode = "EMPTY"
            data.networkError.errorTitle = "Empty"
            data.networkError.errorMessage = "Doctor Name is empty !"
            result.postValue(data)

        } else if ((docTpNumber.isNullOrEmpty() )|| (docTpNumber == "null")) {

            data.networkError.errorCode = "EMPTY"
            data.networkError.errorTitle = "Empty"
            data.networkError.errorMessage = "Doctor contact number is empty !"
            result.postValue(data)

        } else if (docTpNumber.length!=11) {

            data.networkError.errorCode = "INVALIDE"
            data.networkError.errorTitle = "Invalid"
            data.networkError.errorMessage = "Invalid phone number ex - 94711111111 !"
            result.postValue(data)

        } else if ((docQualification.isNullOrEmpty()) || (docQualification == "null")) {
            data.networkError.errorCode = "EMPTY"
            data.networkError.errorTitle = "Empty"
            data.networkError.errorMessage = "Doctor Qualification is empty !"
            result.postValue(data)

        } else if ((docSpecList == null) || (docSpecList.isEmpty())) {

            data.networkError.errorCode = "EMPTY"
            data.networkError.errorTitle = "Empty"
            data.networkError.errorMessage = "Please add doctor specialties"
            result.postValue(data)

        } else {

            loding.set(true)

            val userId = sharedPref.getInt(USER_ID, 0)

            val docJsonObject = JsonObject()



            docJsonObject.addProperty("name", docName)
            docJsonObject.addProperty("ContactNo", docTpNumber)
            docJsonObject.addProperty("RegistrationNo", docRegNumber)
            docJsonObject.addProperty("Qualification", docQualification)
            docJsonObject.addProperty("CreatedByID", userId)
            docJsonObject.addProperty("IsAfterSuggestion", docDuplicateStatus)

            val docSpecJsonArr = JsonArray()

            for (specItem in docSpecList) {
                val ob = JsonObject()
                ob.addProperty("ID",specItem.specID )
                docSpecJsonArr.add(ob)
            }


            docJsonObject.add("SpecializationList", docSpecJsonArr)




          /*  apiInterface.saveDoctor(docJsonObject)
                .subscribeOn(Schedulers.io())
                .doOnError { it }
                .doOnTerminate { }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Doctor> {
                    override fun onSubscribe(d: Disposable) {
                    }
                    override fun onNext(log: Doctor) {
                        data = log
                    }
                    override fun onError(e: Throwable) {
                        data.networkError = networkErrorHandler(e)
                        result.postValue(data)
                        loding.set(false)
                    }
                    override fun onComplete() {
                        result.postValue(data)
                        loding.set(false)
                    }
                })*/




            var test = Doctor()

            test.doctorsStatus = false
            test.isDoctorsDuplicate = true


            var testList = ArrayList<DoctorList>()

            testList.add(DoctorList(1, "D1", "", "","",""))
            testList.add(DoctorList(2, "D2", "", "","",""))
            testList.add(DoctorList(3, "D3", "", "","",""))

            testList.add(DoctorList(4, "D4", "", "","",""))
            testList.add(DoctorList(5, "D5", "", "","",""))
            testList.add(DoctorList(6, "D6", "", "","",""))



            test.approvedDoctorList = testList

            loding.set(false)

            result.postValue(test)





        }


        return result
    }

    fun getApprovedLocation(loding: ObservableField<Boolean>): MutableLiveData<Locations> {
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



    fun assignDoctorsToLocation(
        loding: ObservableField<Boolean>,
        doc: ArrayList<DoctorList>,
        lcoList: ArrayList<LocationsList>
    ): MutableLiveData<Doctor> {
        val result = MutableLiveData<Doctor>()
        var data = Doctor()


        var sortedlcoList = ArrayList<LocationsList>()
        sortedlcoList = lcoList.filter { it.isSelect } as ArrayList<LocationsList>

        var sortedDoc = ArrayList<DoctorList>()
        sortedDoc = doc.filter { it.isSelect } as ArrayList<DoctorList>



        if (!Utils.checkInternetConnection(app)) {
            data.networkError.errorCode = "INT"
            data.networkError.errorTitle = "Connection"
            data.networkError.errorMessage = "No internet connection !"

            result.postValue(data)
        } else if (sortedDoc.isNullOrEmpty()) {
            data.networkError.errorCode = "EMPTY"
            data.networkError.errorTitle = "Empty"
            data.networkError.errorMessage = "Please select Doctor"
            result.postValue(data)

        }
        else if (sortedlcoList.isNullOrEmpty()) {
            data.networkError.errorCode = "EMPTY"
            data.networkError.errorTitle = "Empty"
            data.networkError.errorMessage = "Please select Location"
            result.postValue(data)
        }

        else {

            loding.set(true)
            val userId = sharedPref.getInt(USER_ID, 0)

            val docJsonObject = JsonObject()

           /* docJsonObject.addProperty("name", docName)
            docJsonObject.addProperty("ContactNo", docTpNumber)
            docJsonObject.addProperty("RegistrationNo", docRegNumber)
            docJsonObject.addProperty("Qualification", docQualification)
            docJsonObject.addProperty("CreatedByID", userId)
            docJsonObject.addProperty("IsAfterSuggestion", docDuplicateStatus)

            val docSpecJsonArr = JsonArray()

            for (specItem in docSpecList) {
                val ob = JsonObject()
                ob.addProperty("ID",specItem.specID )
                docSpecJsonArr.add(ob)
            }


            docJsonObject.add("SpecializationList", docSpecJsonArr)*/



            var test = Doctor()

            test.doctorsStatus = true


            var testList = ArrayList<DoctorList>()

            testList.add(DoctorList(1, "D1", "", "","",""))
            testList.add(DoctorList(2, "D2", "", "","",""))
            testList.add(DoctorList(3, "D3", "", "","",""))

            testList.add(DoctorList(4, "D4", "", "","",""))
            testList.add(DoctorList(5, "D5", "", "","",""))
            testList.add(DoctorList(6, "D6", "", "","",""))



            test.approvedDoctorList = testList

            loding.set(false)

            result.postValue(test)





        }


        return result
    }




}