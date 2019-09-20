package emerge.projects.repsolutions.ui.login.mvvm

import android.app.Application
import android.view.View
import android.widget.CheckBox
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import emerge.projects.repsolutions.data.modeldata.User

open class LoginViewModel(application: Application) : AndroidViewModel(application) {



    var loginRepository: LoginRepo = LoginRepo(application)
    var textVersion = ObservableField<String>()

    var editTextPassword = MutableLiveData<String>()
    var editTextUserName = MutableLiveData<String>()

    val isLoading = ObservableField<Boolean>()
    var isRememberMeChecked = false


    var userLoginRespond: MutableLiveData<User> = MutableLiveData<User>()



    fun checkUserCredential(): MutableLiveData<Boolean>  {
        return loginRepository.getUserSaveCredential()
    }

    fun loginValidation(): MutableLiveData<User>{
       return loginRepository.login(editTextPassword,editTextUserName,isLoading,isRememberMeChecked)
    }


    fun setApplicationVersion() {
        loginRepository.getAppVersionRepo(textVersion)
    }

    fun onRememberMeCheckedChanged(v: View) {
        isRememberMeChecked = (v as CheckBox).isChecked
    }
}