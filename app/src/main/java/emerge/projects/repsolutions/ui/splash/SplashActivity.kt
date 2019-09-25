package emerge.projects.repsolutions.ui.splash

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.ui.home.activity.HomeActivity
import emerge.projects.repsolutions.ui.login.activity.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {

           /* val intent = Intent(this, LoginActivity::class.java)*/
            val intent = Intent(this, HomeActivity::class.java)
            val bndlanimation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
            startActivity(intent, bndlanimation)

            this.finish()

        }, 3000)
    }
}
