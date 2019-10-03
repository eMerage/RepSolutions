package emerge.projects.repsolutions.ui.doctors.doctorupdate.activity

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.Doctor
import emerge.projects.repsolutions.data.modeldata.NetworkError
import emerge.projects.repsolutions.databinding.ActivityDoctorsUpdateBinding
import emerge.projects.repsolutions.ui.doctors.doctorslocationassign.activity.DoctorLocationAssignActivity
import emerge.projects.repsolutions.ui.doctors.doctorsnew.activity.DoctorNewActivity
import emerge.projects.repsolutions.ui.doctors.mvvm.DoctorModelView
import emerge.projects.repsolutions.ui.home.activity.HomeActivity
import emerge.projects.repsolutions.ui.location.locationlist.activity.LoctaionListActivity
import emerge.projects.repsolutions.ui.location.locationnew.activity.LocationNewActivity
import emerge.projects.repsolutions.ui.visits.doctorsvisitslist.activity.DoctorsVisitsActivity
import emerge.projects.repsolutions.ui.visits.doctorvisitnew.activity.DoctorsNewVisitsActivity
import kotlinx.android.synthetic.main.activity_doctors_update.*

class DoctorsUpdateActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var bindingUpdateDoctors: ActivityDoctorsUpdateBinding
    lateinit var viewModelUpdateDoctor: DoctorModelView


    lateinit var drawerLayout: DrawerLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingUpdateDoctors = DataBindingUtil.setContentView(this, R.layout.activity_doctors_update)
        bindingUpdateDoctors.lifecycleOwner = this
        viewModelUpdateDoctor = ViewModelProviders.of(this).get(DoctorModelView::class.java)
        bindingUpdateDoctors.updatedoctors = viewModelUpdateDoctor



        setSupportActionBar(toolbar_updatedoctors)


        drawerLayout = findViewById(R.id.drawer_layout_updatedoctors)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar_updatedoctors,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view_updatedoctors.setNavigationItemSelectedListener(this)

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


    fun getApprovedDoctors(){

        viewModelUpdateDoctor!!.getApprovedDoctors().observe(this, Observer<Doctor> {
            it?.let { result ->

                if(result.doctorsStatus){

/*

                    var docAdaptar = DoctorApprovedListAdaptor(doctorsList.value!!.approvedDoctorList,this)
                    recyclerView_doctors_assign.adapter =docAdaptar

                    docAdaptar.setOnItemClickListener(object : DoctorApprovedListAdaptor.ClickListener {
                        override fun onClick(doc: DoctorList, aView: View) {

                        }
                    })
*/


                }else{
                    errorAlertDialog(result.networkError)
                }

            }
        })
    }






    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Dashboard" -> {
                val intentDocVists = Intent(this, HomeActivity::class.java)
                val bndlanimationDocVists = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }
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
            "New Location" -> {
                val intentDocVists = Intent(this, LocationNewActivity::class.java)
                val bndlanimationDocVists =
                    ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
                        .toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }
            "New Doctor" -> {
                val intentDocVists = Intent(this, DoctorNewActivity::class.java)
                val bndlanimationDocVists =
                    ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
                        .toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }
            "Assign Location to Doctors" -> {
                val intentDocVists = Intent(this, DoctorLocationAssignActivity::class.java)
                val bndlanimationDocVists = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }


        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun addMenuItemInNavMenuDrawer() {

        var menu = nav_view_updatedoctors.menu
        menu.add("Dashboard")
        menu.add("Doctor's Visits")
        menu.add("New Doctor's Visits")
        menu.add("New Doctor")
        menu.add("Assign Location to Doctors")
        menu.add("Locations")
        menu.add("New Location")


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
