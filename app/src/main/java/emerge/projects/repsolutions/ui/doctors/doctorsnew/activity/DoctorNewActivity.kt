package emerge.projects.repsolutions.ui.doctors.doctorsnew.activity

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.NetworkError
import emerge.projects.repsolutions.databinding.ActivityDoctorNewBinding
import emerge.projects.repsolutions.ui.doctors.doctors.activity.DoctorsActivity
import emerge.projects.repsolutions.ui.doctors.doctorsvisitslist.activity.DoctorsVisitsActivity
import emerge.projects.repsolutions.ui.doctors.doctorvisitnew.activity.DoctorsNewVisitsActivity
import emerge.projects.repsolutions.ui.doctors.mvvm.DoctorModelView
import emerge.projects.repsolutions.ui.location.locationlist.activity.LoctaionListActivity
import kotlinx.android.synthetic.main.activity_doctor_new.*

class DoctorNewActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {



    lateinit var bindingNewDoctor: ActivityDoctorNewBinding
    lateinit var viewModelNewDoctor: DoctorModelView

    lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingNewDoctor = DataBindingUtil.setContentView(this, R.layout.activity_doctor_new)
        bindingNewDoctor.lifecycleOwner = this
        viewModelNewDoctor = ViewModelProviders.of(this).get(DoctorModelView::class.java)
        bindingNewDoctor.newDoctor = viewModelNewDoctor


        val toolbar: Toolbar = findViewById(R.id.toolbar_newdoctor)
        setSupportActionBar(toolbar)


        drawerLayout = findViewById(R.id.drawer_layout_newdoctor)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view_newdoctor)
        navView.setNavigationItemSelectedListener(this)

        addMenuItemInNavMenuDrawer()


    }


    override fun onStart() {
        super.onStart()

    }
    override fun isDestroyed(): Boolean {
        return super.isDestroyed()

    }

    override fun onStop() {
        super.onStop()


    }

    override fun onLowMemory() {
        super.onLowMemory()

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Doctor's Visits" -> {
                val intentDocVists = Intent(this, DoctorsVisitsActivity::class.java)
                val bndlanimationDocVists =
                    ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
                        .toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }

            "New Doctor's Visits" -> {
                val intentDocVists = Intent(this, DoctorsNewVisitsActivity::class.java)
                val bndlanimationDocVists =
                    ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
                        .toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }
            "Locations" -> {
                val intentDocVists = Intent(this, LoctaionListActivity::class.java)
                val bndlanimationDocVists =
                    ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
                        .toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }
            "Doctors" -> {
                val intentDocVists = Intent(this, DoctorsActivity::class.java)
                val bndlanimationDocVists =
                    ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
                        .toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }


        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun addMenuItemInNavMenuDrawer() {

        var menu = nav_view_newdoctor.menu
        menu.add("Doctor's Visits")
        menu.add("New Doctor's Visits")
        menu.add("Locations")
        menu.add("New Locations")
        menu.add("Doctors")


        //  menu.add(0, MENU_EDIT, Menu.NONE, R.string.itemName).setIcon(R.drawable.itemDrawable);

    }

    fun errorAlertDialog(networkError: NetworkError) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(networkError.errorTitle)
        alertDialogBuilder.setMessage(networkError.errorMessage)
        alertDialogBuilder.setPositiveButton(
            "OK",
            DialogInterface.OnClickListener { _, _ -> return@OnClickListener })
        alertDialogBuilder.show()
    }

}
