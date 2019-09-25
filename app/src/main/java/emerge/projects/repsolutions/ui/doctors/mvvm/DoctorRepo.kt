package emerge.projects.repsolutions.ui.doctors.mvvm

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
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

class DoctorRepo (application: Application){


    var app: Application = application
    var apiInterface: APIInterface = ApiClient.client(application)

    val sharedPref = app?.getSharedPreferences("filename1", Context.MODE_PRIVATE)
    var networkErrorHandler: NetworkErrorHandler = NetworkErrorHandler()



    private val USER_ID = "userID"
    private val USER_TYPE = "userType"
    private val USER_NAME = "userName"
    private val USER_EMAIL = "userEmail"
    private val USER_REMEMBER = "userRemember"




    fun getDoctorsVisits(loding : ObservableField<Boolean>) : MutableLiveData<VisitsDoctors> {
        val result = MutableLiveData<VisitsDoctors>()
        var data = VisitsDoctors()
        loding.set(true)

        if (!Utils.checkInternetConnection(app))
            Toast.makeText(app, "No internet connection you will miss the latest information ", Toast.LENGTH_LONG).show()

        val userId = sharedPref.getInt(USER_ID,0)

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


    fun getApprovedDoctors(loding : ObservableField<Boolean>,lat : Double,log : Double) : MutableLiveData<Doctor> {
        val result = MutableLiveData<Doctor>()
        var data = Doctor()
        loding.set(true)


        if(!Utils.checkInternetConnection(app)){
            Toast.makeText(app, "No internet connection !", Toast.LENGTH_LONG).show()
        }else{
            val userId = sharedPref.getInt(USER_ID,0)


            var test = Doctor()

            test.doctorsStatus = true


            var testList = ArrayList<DoctorList>()

            testList.add(DoctorList(1,"D1"))
            testList.add(DoctorList(2,"D2"))
            testList.add(DoctorList(3,"D3"))



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


    fun getDoctorsLocations(loding : ObservableField<Boolean>,docID : Int,lat : Double,log : Double) : MutableLiveData<Locations> {

        val result = MutableLiveData<Locations>()
        var data = Locations()
        loding.set(true)

        if(!Utils.checkInternetConnection(app)){
            Toast.makeText(app, "No internet connection !", Toast.LENGTH_LONG).show()
        }else{




            var test = Locations()

            test.locationsStatus = true


            var testList = ArrayList<LocationsList>()

            testList.add(LocationsList(1,"L1"))
            testList.add(LocationsList(2,"L2"))
            testList.add(LocationsList(3,"L3"))



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
                        data.locationsNetworkError = networkErrorHandler(e)
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


    fun getUsersProducts(loding : ObservableField<Boolean>) : MutableLiveData<Product> {
        val result = MutableLiveData<Product>()
        var data = Product()
        loding.set(true)

        if(!Utils.checkInternetConnection(app)){
            Toast.makeText(app, "No internet connection !", Toast.LENGTH_LONG).show()
        }else{
            val userId = sharedPref.getInt(USER_ID,0)


            var test = Product()

            test.productStatus = true


            var testList = ArrayList<ProductList>()

            testList.add(ProductList(1,"P1"))
            testList.add(ProductList(2,"P2"))
            testList.add(ProductList(3,"P3"))

            testList.add(ProductList(4,"P4"))
            testList.add(ProductList(5,"P5"))
            testList.add(ProductList(6,"P6"))

            testList.add(ProductList(7,"P7"))
            testList.add(ProductList(8,"P8"))
            testList.add(ProductList(9,"P9"))



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


    fun getSampleProducts(loding : ObservableField<Boolean>) : MutableLiveData<Product> {
        val result = MutableLiveData<Product>()
        var data = Product()
        loding.set(true)

        if(!Utils.checkInternetConnection(app)){
            Toast.makeText(app, "No internet connection !", Toast.LENGTH_LONG).show()
        }else{
            val userId = sharedPref.getInt(USER_ID,0)




            var test = Product()

            test.productStatus = true


            var testList = ArrayList<ProductList>()

            testList.add(ProductList(1,"S1"))
            testList.add(ProductList(2,"S2"))
            testList.add(ProductList(3,"S3"))

            testList.add(ProductList(4,"S4"))
            testList.add(ProductList(5,"S5"))
            testList.add(ProductList(6,"S6"))

            testList.add(ProductList(7,"S7"))
            testList.add(ProductList(8,"S8"))
            testList.add(ProductList(9,"S9"))



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


    fun getDoctors(loding : ObservableField<Boolean>) : MutableLiveData<Doctor> {
        val result = MutableLiveData<Doctor>()
        var data = Doctor()

        loding.set(true)

        if (!Utils.checkInternetConnection(app))
            Toast.makeText(app, "No internet connection you will miss the latest information ", Toast.LENGTH_LONG).show()

        val userId = sharedPref.getInt(USER_ID,0)



        var test = Doctor()

        test.doctorsStatus = true


        var testList = ArrayList<DoctorList>()

        var testSpec1 = ArrayList<SpecializationList>()
        testSpec1.add(SpecializationList(1,"Pediatrician"))
        testSpec1.add(SpecializationList(2,"Surgeon"))



        testList.add(DoctorList(1,"Channd D Ranasinge","","CDR","0716607483","RDG558",
            "MBBS","","","Saman",false,true,
            testSpec1,
            ArrayList<LocationsList>(),ArrayList<ProductList>(),"Pediatrician/Surgeon","Asiri/Nawaloka","Pending"))


        var testSpec2 = ArrayList<SpecializationList>()
        testSpec2.add(SpecializationList(2,"Surgeon"))



        testList.add(DoctorList(1,"Lakshmen Obeysekara","","CDR","0716607483","RDG558",
            "MBBS","","","Kamal",false,true,testSpec2,
            ArrayList<LocationsList>(),ArrayList<ProductList>(),"Surgeon","Asiri/Nawaloka","Pending"))


        var testSpec3 = ArrayList<SpecializationList>()
        testSpec3.add(SpecializationList(3,"Oncologist"))

        testList.add(DoctorList(1,"Priyantha Madawa","","CDR","0716607483","RDG558",
            "MBBS","","","Saman",false,true,testSpec3,
            ArrayList<LocationsList>(),ArrayList<ProductList>(),"Oncologist","Asiri/Nawaloka","Pending"))


        var testSpec4 = ArrayList<SpecializationList>()
        testSpec4.add(SpecializationList(3,"Oncologist"))

        testList.add(DoctorList(1,"R.S. Jayatilake","","CDR","0716607483","RDG558",
            "MBBS","","","Ruwan",false,true,testSpec4,
            ArrayList<LocationsList>(),ArrayList<ProductList>(),"Oncologist","Asiri/Nawaloka","Pending"))


        var testSpec5 = ArrayList<SpecializationList>()
        testSpec5.add(SpecializationList(56,"Neurologist"))

        testList.add(DoctorList(1,"Panduka Jayasekara","","CDR","0716607483","RDG558",
            "MBBS","","","Aman",false,true,testSpec5,
            ArrayList<LocationsList>(),ArrayList<ProductList>(),"Neurologist","Asiri/Nawaloka","Pending"))


        testList.add(DoctorList(1,"Jayamini Horadugoda","","CDR","0716607483","RDG558",
            "MBBS","","","Suman",false,true,ArrayList<SpecializationList>(),
            ArrayList<LocationsList>(),ArrayList<ProductList>(),"Embryologist","Asiri/Nawaloka","Pending"))



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


    fun searchDoctors(searchName : String,doctorsList : ArrayList<DoctorList>): MutableLiveData<ArrayList<DoctorList>> {

        var result = MutableLiveData<ArrayList<DoctorList>>()
        var data = ArrayList<DoctorList>()

        var patternName = searchName

        if((searchName.isNullOrEmpty() ) ||  (searchName == "all")  ||  (searchName == "") ){
            result.postValue(doctorsList)
        }else{
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

                var listDocStatus =doc.doctorStats
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



}