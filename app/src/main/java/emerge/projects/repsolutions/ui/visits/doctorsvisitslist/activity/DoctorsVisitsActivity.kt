package emerge.projects.repsolutions.ui.visits.doctorsvisitslist.activity

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.VisitsDoctors
import emerge.projects.repsolutions.databinding.ActivityDoctorsVisitsBinding
import emerge.projects.repsolutions.ui.doctors.doctors.activity.DoctorsActivity
import emerge.projects.repsolutions.ui.doctors.doctorslocationassign.activity.DoctorLocationAssignActivity
import emerge.projects.repsolutions.ui.doctors.doctorsnew.activity.DoctorNewActivity
import emerge.projects.repsolutions.ui.doctors.doctorupdate.activity.DoctorsUpdateActivity
import emerge.projects.repsolutions.ui.location.locationlist.activity.LoctaionListActivity
import emerge.projects.repsolutions.ui.location.locationnew.activity.LocationNewActivity
import emerge.projects.repsolutions.ui.visits.doctorsvisitslist.adaptar.DoctorVisitsAdaptor
import emerge.projects.repsolutions.ui.doctors.mvvm.DoctorModelView
import emerge.projects.repsolutions.ui.visits.doctorvisitnew.activity.DoctorsNewVisitsActivity
import emerge.projects.repsolutions.ui.home.activity.HomeActivity
import kotlinx.android.synthetic.main.activity_doctors_visits.*

class DoctorsVisitsActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {


    lateinit var bindingDoctorVisits: ActivityDoctorsVisitsBinding
    lateinit var viewModelDoctorVisits: DoctorModelView


    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        bindingDoctorVisits = DataBindingUtil.setContentView(this, R.layout.activity_doctors_visits)
        bindingDoctorVisits.lifecycleOwner = this
        viewModelDoctorVisits = ViewModelProviders.of(this).get(DoctorModelView::class.java)
        bindingDoctorVisits.doctorsVistis = viewModelDoctorVisits


        val toolbar: Toolbar = findViewById(R.id.toolbar_docvisits)
        setSupportActionBar(toolbar)


        drawerLayout = findViewById(R.id.drawer_layout_docvisits)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view_docvisits)
        navView.setNavigationItemSelectedListener(this)

        addMenuItemInNavMenuDrawer()




    }

    override fun onStart() {
        super.onStart()
        getVisitsList()
        swiperefresh_doc_visits.setOnRefreshListener {
            getVisitsList()
        }
    }




    fun getVisitsList(){


        viewModelDoctorVisits!!.getDoctorsVisits().observe(this, Observer<VisitsDoctors> {
            it?.let { result ->
                swiperefresh_doc_visits.isRefreshing = false
                if(result.visitsDoctorsStatus){
                    recyclerView_docvisits.adapter = DoctorVisitsAdaptor(result.visitsDoctorsList,this)
                }else{
                    errorAlertDialog(result.networkError.errorTitle,result.networkError.errorMessage)
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

            "New Doctor's Visits" -> {
                val intentDocVists = Intent(this, DoctorsNewVisitsActivity::class.java)
                val bndlanimationDocVists = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }

            "Locations" -> {
                val intentDocVists = Intent(this, LoctaionListActivity::class.java)
                val bndlanimationDocVists = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }
            "New Location" -> {
                val intentDocVists = Intent(this, LocationNewActivity::class.java)
                val bndlanimationDocVists = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }
            "Doctors" -> {
                val intentDocVists = Intent(this, DoctorsActivity::class.java)
                val bndlanimationDocVists = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }
            "New Doctor" -> {
                val intentDocVists = Intent(this, DoctorNewActivity::class.java)
                val bndlanimationDocVists = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }
            "Assign Location to Doctors" -> {
                val intentDocVists = Intent(this, DoctorLocationAssignActivity::class.java)
                val bndlanimationDocVists = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }
            "Update Doctors" -> {
                val intentDocVists = Intent(this, DoctorsUpdateActivity::class.java)
                val bndlanimationDocVists = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }

        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun addMenuItemInNavMenuDrawer() {

        var menu = nav_view_docvisits.menu
        menu.add("Dashboard")
        menu.add("New Doctor's Visits")
        menu.add("Doctors")
        menu.add("New Doctor")
        menu.add("Assign Location to Doctors")
        menu.add("Update Doctors")
        menu.add("Locations")
        menu.add("New Location")




        //  menu.add(0, MENU_EDIT, Menu.NONE, R.string.itemName).setIcon(R.drawable.itemDrawable);

    }


    fun errorAlertDialog(title : String?,message : String?){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton(
            "OK",
            DialogInterface.OnClickListener { _, _ -> return@OnClickListener })
        alertDialogBuilder.show()

    }
}