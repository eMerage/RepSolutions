package emerge.projects.repsolutions.ui.doctors.doctors.activity

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
import emerge.projects.repsolutions.data.modeldata.Doctor
import emerge.projects.repsolutions.data.modeldata.DoctorList
import emerge.projects.repsolutions.data.modeldata.NetworkError
import emerge.projects.repsolutions.databinding.ActivityDoctorsBinding
import emerge.projects.repsolutions.ui.doctors.doctors.adaptar.DoctorListAdaptor
import emerge.projects.repsolutions.ui.doctors.doctorsnew.activity.DoctorNewActivity
import emerge.projects.repsolutions.ui.doctors.doctorsvisitslist.activity.DoctorsVisitsActivity
import emerge.projects.repsolutions.ui.doctors.mvvm.DoctorModelView
import emerge.projects.repsolutions.ui.doctors.doctorvisitnew.activity.DoctorsNewVisitsActivity
import emerge.projects.repsolutions.ui.location.locationlist.activity.LoctaionListActivity
import emerge.projects.repsolutions.ui.location.locationnew.activity.LocationNewActivity
import kotlinx.android.synthetic.main.activity_doctors.*

class DoctorsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var bindingDoctorsList: ActivityDoctorsBinding
    lateinit var viewModelDoctorList: DoctorModelView


    lateinit var drawerLayout: DrawerLayout

    var doctorsList : ArrayList<DoctorList>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctors)

        bindingDoctorsList = DataBindingUtil.setContentView(this, R.layout.activity_doctors)
        bindingDoctorsList.lifecycleOwner = this
        viewModelDoctorList = ViewModelProviders.of(this).get(DoctorModelView::class.java)
        bindingDoctorsList.doctors = viewModelDoctorList


        val toolbar: Toolbar = findViewById(R.id.toolbar_doctorslist)
        setSupportActionBar(toolbar)


        drawerLayout = findViewById(R.id.drawer_layout_doctorslist)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view_doctorslist)
        navView.setNavigationItemSelectedListener(this)

        addMenuItemInNavMenuDrawer()
        handleIntent(intent)

    }

    override fun onStart() {
        super.onStart()


        getDoctors()
        swiperefresh_doctorslist.setOnRefreshListener {
            getDoctors()
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
            doctorsList?.let { searchtDoctors(query, it) }
        }

    }

    fun getDoctors(){
        viewModelDoctorList!!.getDoctors().observe(this, Observer<Doctor> {
            it?.let { result ->
                swiperefresh_doctorslist.isRefreshing = false
                doctorsList = result.approvedDoctorList
                if(result.doctorsStatus){
                    recyclerView_doctorslist.adapter = DoctorListAdaptor(result.approvedDoctorList,this)
                }else{
                    errorAlertDialog(result.networkError)
                }


            }
        })

    }

    fun searchtDoctors(searchText : String,doctorsList : ArrayList<DoctorList>){
        viewModelDoctorList!!.searchDoctors(searchText,doctorsList).observe(this, Observer<ArrayList<DoctorList>> {
            it?.let { result ->
                if(result.isEmpty()){
                    Toast.makeText(this, "No search results found", Toast.LENGTH_LONG).show()
                }else{
                    recyclerView_doctorslist.adapter = DoctorListAdaptor(result,this)

                }

            }
        })

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


        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun addMenuItemInNavMenuDrawer() {

        var menu = nav_view_doctorslist.menu
        menu.add("Doctor's Visits")
        menu.add("New Doctor's Visits")
        menu.add("Locations")
        menu.add("New Location")
        menu.add("New Doctor")

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
