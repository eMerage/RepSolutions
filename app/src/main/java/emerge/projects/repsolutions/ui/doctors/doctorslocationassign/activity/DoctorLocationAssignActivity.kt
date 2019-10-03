package emerge.projects.repsolutions.ui.doctors.doctorslocationassign.activity

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.*
import emerge.projects.repsolutions.databinding.ActivityDoctorsLocationAssignBinding
import emerge.projects.repsolutions.ui.doctors.doctors.activity.DoctorsActivity
import emerge.projects.repsolutions.ui.doctors.doctorslocationassign.adaptar.DoctorApprovedListAdaptor
import emerge.projects.repsolutions.ui.doctors.doctorslocationassign.adaptar.LocationApprovedListAdaptor
import emerge.projects.repsolutions.ui.doctors.doctorsnew.activity.DoctorNewActivity
import emerge.projects.repsolutions.ui.doctors.doctorupdate.activity.DoctorsUpdateActivity
import emerge.projects.repsolutions.ui.visits.doctorsvisitslist.activity.DoctorsVisitsActivity
import emerge.projects.repsolutions.ui.visits.doctorvisitnew.activity.DoctorsNewVisitsActivity
import emerge.projects.repsolutions.ui.visits.doctorvisitnew.adaptor.AutocompleteDoctorsAdaptor
import emerge.projects.repsolutions.ui.doctors.mvvm.DoctorModelView
import emerge.projects.repsolutions.ui.home.activity.HomeActivity
import emerge.projects.repsolutions.ui.location.locationlist.activity.LoctaionListActivity
import emerge.projects.repsolutions.ui.location.locationnew.activity.LocationNewActivity
import emerge.projects.repsolutions.ui.location.locationuserassign.adaptar.AutocompleteLocationsAdaptor
import kotlinx.android.synthetic.main.activity_doctors_location_assign.*

class DoctorLocationAssignActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{


    lateinit var bindingDoctorsAssign: ActivityDoctorsLocationAssignBinding
    lateinit var viewModelDoctorAssign: DoctorModelView


    lateinit var drawerLayout: DrawerLayout


    var doctorsList = MutableLiveData<Doctor>()
    var locationList = MutableLiveData<Locations>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingDoctorsAssign = DataBindingUtil.setContentView(this, R.layout.activity_doctors_location_assign)
        bindingDoctorsAssign.lifecycleOwner = this
        viewModelDoctorAssign = ViewModelProviders.of(this).get(DoctorModelView::class.java)
        bindingDoctorsAssign.doctorsassign = viewModelDoctorAssign



        val toolbar: Toolbar = findViewById(R.id.toolbar_doctors_assign)
        setSupportActionBar(toolbar)


        drawerLayout = findViewById(R.id.drawer_layout_doctors_assign)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view_doctors_assign)
        navView.setNavigationItemSelectedListener(this)

        addMenuItemInNavMenuDrawer()

    }


    override fun onStart() {
        super.onStart()

        getApprovedDoctors()
        getApprovedLocations()



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

    fun assignDoctorsNoClick(view: View){

        viewModelDoctorAssign!!.assignDoctorsToLocation().observe(this, Observer<Doctor> {
            it?.let { result ->
                if(result.doctorsStatus){
                    Toast.makeText(this, "Complain added already to this visits", Toast.LENGTH_LONG).show()

                }else{
                    errorAlertDialog(result.networkError)
                }

            }
        })

    }


    fun getApprovedDoctors(){
        viewModelDoctorAssign!!.getApprovedDoctors().observe(this, Observer<Doctor> {
            it?.let { result ->
                doctorsList.value = result

                if(result.doctorsStatus){

                    var docAdaptar = DoctorApprovedListAdaptor(doctorsList.value!!.approvedDoctorList,this)
                    recyclerView_doctors_assign.adapter =docAdaptar

                    docAdaptar.setOnItemClickListener(object : DoctorApprovedListAdaptor.ClickListener {
                        override fun onClick(doc: DoctorList, aView: View) {

                        }
                    })

                    val adapterAutocomplete = AutocompleteDoctorsAdaptor(
                        this,
                        R.layout.textview_autocomplete,
                        doctorsList.value!!.approvedDoctorList
                    )
                    autoCompleteTextView_doctors.setAdapter(adapterAutocomplete)

                    updateDoctorSelect(docAdaptar)

                }else{
                    errorAlertDialog(result.networkError)
                }

            }
        })
    }


    fun updateDoctorSelect(adaptor : DoctorApprovedListAdaptor){
        viewModelDoctorAssign.docItemAddRespons.observe(this, Observer<Boolean> {
            it?.let { result ->
                adaptor.notifyDataSetChanged()
                autoCompleteTextView_doctors.setText("")
            }
        })

    }



    fun getApprovedLocations(){

        viewModelDoctorAssign!!.getApprovedLocations().observe(this, Observer<Locations> {
            it?.let { result ->
                locationList.value = result

                if(result.locationsStatus){

                    var locAdaptar = LocationApprovedListAdaptor(locationList.value!!.locationsList,this)
                    recyclerView_doctors_assign_locations.adapter =locAdaptar

                    locAdaptar.setOnItemClickListener(object : LocationApprovedListAdaptor.ClickListener {
                        override fun onClick(loc: LocationsList, aView: View) {

                        }
                    })
                    val adapterAutocomplete = AutocompleteLocationsAdaptor(
                        this,
                        R.layout.textview_autocomplete,
                        locationList.value!!.locationsList
                    )
                    autocompletetextView_locations.setAdapter(adapterAutocomplete)
                    updateLocationSelect(locAdaptar)

                }else{
                    errorAlertDialog(result.locationsNetworkError)
                }

            }
        })
    }


    fun updateLocationSelect(adaptor : LocationApprovedListAdaptor){
        viewModelDoctorAssign.locationItemAddRespons.observe(this, Observer<Boolean> {
            it?.let { result ->
                adaptor.notifyDataSetChanged()
                autocompletetextView_locations.setText("")
            }
        })

    }

    fun addMenuItemInNavMenuDrawer() {

        var menu = nav_view_doctors_assign.menu
        menu.add("Dashboard")
        menu.add("Doctor's Visits")
        menu.add("New Doctor's Visits")
        menu.add("Doctors")
        menu.add("New Doctor")
        menu.add("Update Doctors")
        menu.add("Locations")
        menu.add("New Location")

        //  menu.add(0, MENU_EDIT, Menu.NONE, R.string.itemName).setIcon(R.drawable.itemDrawable);

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
            "Doctors" -> {
                val intentDocVists = Intent(this, DoctorsActivity::class.java)
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
