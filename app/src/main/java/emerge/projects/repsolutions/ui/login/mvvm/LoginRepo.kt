package emerge.projects.repsolutions.ui.login.mvvm

import android.app.Application
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.User
import emerge.projects.repsolutions.services.api.APIInterface
import emerge.projects.repsolutions.services.api.ApiClient
import emerge.projects.repsolutions.services.network.NetworkErrorHandler
import emerge.projects.repsolutions.services.network.Utils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginRepo(application: Application) {





    var app: Application = application
    var apiInterface: APIInterface = ApiClient.client(application)

    val sharedPref = app?.getSharedPreferences("filename1", Context.MODE_PRIVATE)
    var networkErrorHandler: NetworkErrorHandler = NetworkErrorHandler()



    private val USER_ID = "userID"
    private val USER_TYPE = "userType"
    private val USER_NAME = "userName"
    private val USER_EMAIL = "userEmail"
    private val USER_REMEMBER = "userRemember"



    fun login(
        password: MutableLiveData<String>,
        userName: MutableLiveData<String>,
        loading: ObservableField<Boolean>,
        isRememberMeChecked: Boolean
    ): MutableLiveData<User> {


        val result = MutableLiveData<User>()
        var data = User()

        if (!Utils.checkInternetConnection(app)) {
            data.loginNetworkError.errorCode = "INT"
            data.loginNetworkError.errorTitle = "Connection"
            data.loginNetworkError.errorMessage = "No internet connection !"

            result.postValue(data)

        } else if (userName.value.isNullOrEmpty()) {
            data.loginNetworkError.errorCode = "EMPTY"
            data.loginNetworkError.errorTitle = "Empty"
            data.loginNetworkError.errorMessage = "Please Enter your User Name !"

            result.postValue(data)

        } else if (password.value.isNullOrEmpty()) {
            data.loginNetworkError.errorCode = "EMPTY"
            data.loginNetworkError.errorTitle = "Empty"
            data.loginNetworkError.errorMessage = "Please Enter your password !"

            result.postValue(data)

        } else {
            loading.set(true)


           FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->

                apiInterface.validateUser(userName.value.toString(), password.value.toString(), task.result?.token!!)
                    .subscribeOn(Schedulers.io())
                    .doOnError { it }
                    .doOnTerminate { }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<User> {
                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(log: User) {
                            data = log
                        }

                        override fun onError(e: Throwable) {
                            loading.set(false)
                            data.loginNetworkError = networkErrorHandler(e)
                            result.postValue(data)
                        }

                        override fun onComplete() {
                            if(data.userStatus){
                                saveLoginDetails(data,isRememberMeChecked)
                                result.value = data
                            }else{
                                data.loginNetworkError.errorCode = "SERVER"
                                data.loginNetworkError.errorTitle = "Server Error"
                                data.loginNetworkError.errorMessage = "Server Error,Please Try again !"
                                result.value = data
                            }

                            result.postValue(data)
                            loading.set(false)


                        }
                    })
            })
        }
        return result
    }


    fun getUserSaveCredential(): MutableLiveData<Boolean> {

        var isUserLogin = MutableLiveData<Boolean>()

        val userRemember = sharedPref.getBoolean(USER_REMEMBER,false)
      //  val userId = sharedPref.getString(USER_ID, "")

        isUserLogin.postValue(userRemember)

        //isUserLogin.value = (userId?.isNotEmpty()!!) && (userRemember)
        return isUserLogin

    }


    fun saveLoginDetails(data : User,isRememberMeChecked: Boolean){
        with (sharedPref.edit()) { putBoolean(USER_REMEMBER, isRememberMeChecked)
            commit()
        }

        with (sharedPref.edit()) { putInt(USER_ID, data.userID)
            commit()
        }

        with (sharedPref.edit()) { putInt(USER_TYPE, data.userTypeId)
            commit()
        }

        with (sharedPref.edit()) { putString(USER_NAME, data.name)
            commit()
        }


        with (sharedPref.edit()) { putString(USER_EMAIL, data.email)
            commit()
        }

    }

    fun getAppVersionRepo(textViewVersion: ObservableField<String>) {
        var appVersion = MutableLiveData<String>()
        var appLevel: String = app.getString(R.string.app_name)
        var pInfo: PackageInfo? = null
        try {
            pInfo = app.packageManager.getPackageInfo(app.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            appVersion.value = appLevel + "1.0.0"
            e.printStackTrace()
        }
        appVersion.value = pInfo!!.versionName
        textViewVersion.set(appVersion.value)
    }
}