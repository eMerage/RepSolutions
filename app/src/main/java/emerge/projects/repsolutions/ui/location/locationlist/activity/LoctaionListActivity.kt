package emerge.projects.repsolutions.ui.location.locationlist.activity

import android.app.ActivityOptions
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.Locations
import emerge.projects.repsolutions.data.modeldata.LocationsList
import emerge.projects.repsolutions.databinding.ActivityLoctaionListBinding
import emerge.projects.repsolutions.ui.doctors.doctors.activity.DoctorsActivity
import emerge.projects.repsolutions.ui.doctors.doctorslocationassign.activity.DoctorLocationAssignActivity
import emerge.projects.repsolutions.ui.doctors.doctorsnew.activity.DoctorNewActivity
import emerge.projects.repsolutions.ui.doctors.doctorupdate.activity.DoctorsUpdateActivity
import emerge.projects.repsolutions.ui.location.locationlist.adaptar.LocationListAdaptor
import emerge.projects.repsolutions.ui.location.mvvm.LocationModelView
import emerge.projects.repsolutions.ui.location.locationnew.activity.LocationNewActivity
import emerge.projects.repsolutions.ui.visits.doctorsvisitslist.activity.DoctorsVisitsActivity
import emerge.projects.repsolutions.ui.visits.doctorvisitnew.activity.DoctorsNewVisitsActivity
import emerge.projects.repsolutions.ui.home.activity.HomeActivity
import kotlinx.android.synthetic.main.activity_loctaion_list.*

class LoctaionListActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {




    lateinit var bindingLocationList: ActivityLoctaionListBinding
    lateinit var viewModelLocationList: LocationModelView


    lateinit var drawerLayout: DrawerLayout

     var locationList : ArrayList<LocationsList>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        bindingLocationList = DataBindingUtil.setContentView(this, R.layout.activity_loctaion_list)
        bindingLocationList.lifecycleOwner = this
        viewModelLocationList = ViewModelProviders.of(this).get(LocationModelView::class.java)
        bindingLocationList.locations = viewModelLocationList


        val toolbar: Toolbar = findViewById(R.id.toolbar_locationlist)
        setSupportActionBar(toolbar)


        drawerLayout = findViewById(R.id.drawer_layout_locationlist)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view_locationlist)
        navView.setNavigationItemSelectedListener(this)

        addMenuItemInNavMenuDrawer()

        handleIntent(intent)

    }

    override fun onStart() {
        super.onStart()

        getLocationList()
        swiperefresh_locationlist.setOnRefreshListener {
            getLocationList()
        }

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


    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            locationList?.let { serachLocation(query, it) }
        }

    }


    fun getLocationList(){
        viewModelLocationList!!.getLocationList().observe(this, Observer<Locations> {
            it?.let { result ->
                swiperefresh_locationlist.isRefreshing = false
                if(result.locationsStatus){
                    locationList = result.locationsList
                    recyclerView_locationlist.adapter = LocationListAdaptor(result.locationsList,this)
                }else{
                    errorAlertDialog(result.locationsNetworkError.errorTitle,result.locationsNetworkError.errorMessage)
                }

            }
        })
    }

    private fun serachLocation(searchText : String,locationList : ArrayList<LocationsList>){
        viewModelLocationList.searchLocation(searchText,locationList).observe(this, Observer<ArrayList<LocationsList>> {
            it?.let { result ->
                if(result.isEmpty()){
                    Toast.makeText(this, "No search results found", Toast.LENGTH_LONG).show()
                }else{
                    recyclerView_locationlist.adapter = LocationListAdaptor(result,this)

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

        var menu = nav_view_locationlist.menu
        menu.add("Dashboard")
        menu.add("Doctor's Visits")
        menu.add("New Doctor's Visits")
        menu.add("Doctors")
        menu.add("New Doctor")
        menu.add("Assign Location to Doctors")
        menu.add("Update Doctors")
        menu.add("New Location")
        //  menu.add(0, MENU_EDIT, Menu.NONE, R.string.itemName).setIcon(R.drawable.itemDrawable);

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_location_list, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
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
