package emerge.projects.repsolutions.ui.login.activity

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.User
import emerge.projects.repsolutions.databinding.ActivityLoginBinding
import emerge.projects.repsolutions.ui.home.activity.HomeActivity
import emerge.projects.repsolutions.ui.login.mvvm.LoginViewModel

class LoginActivity : AppCompatActivity() {


    lateinit var bindingLogin: ActivityLoginBinding
    lateinit var viewModelLogin: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        bindingLogin = DataBindingUtil.setContentView(this, R.layout.activity_login)
        bindingLogin.lifecycleOwner = this
        viewModelLogin = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        bindingLogin.login = viewModelLogin




    }
    override fun onStart() {
        super.onStart()
        viewModelLogin.setApplicationVersion()

        bindingLogin.login!!.checkUserCredential().observe(this, Observer<Boolean> {
            it?.let { result ->
                if(result){
                    val intent = Intent(this, HomeActivity::class.java)
                    val bndlanimation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                    startActivity(intent, bndlanimation)
                    this.finish()
                }else{

                }
            }
        })



    }



    fun loginOnClick(view : View){
        bindingLogin.login!!.loginValidation().observe(this, Observer<User> {
            it?.let { result ->

                val intent = Intent(this, HomeActivity::class.java)
                val bndlanimation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                startActivity(intent, bndlanimation)
                this.finish()

              /*  if(result.userStatus){
                    val intent = Intent(this, HomeActivity::class.java)
                    val bndlanimation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                    startActivity(intent, bndlanimation)
                    this.finish()
                    Toast.makeText(this, "Welcome " + result.name, Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,result.loginNetworkError.errorMessage, Toast.LENGTH_LONG).show()
                }*/
            }
        })
    }



}
