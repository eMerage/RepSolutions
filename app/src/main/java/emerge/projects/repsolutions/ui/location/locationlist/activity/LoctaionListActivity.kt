package emerge.projects.repsolutions.ui.location.locationlist.activity

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
import emerge.projects.repsolutions.data.modeldata.Locations
import emerge.projects.repsolutions.databinding.ActivityLoctaionListBinding
import emerge.projects.repsolutions.ui.location.locationlist.adaptar.LocationListAdaptor
import emerge.projects.repsolutions.ui.location.mvvm.LocationModelView
import emerge.projects.repsolutions.ui.visitsdoctors.doctorsvisitslist.activity.DoctorsVisitsActivity
import emerge.projects.repsolutions.ui.visitsdoctors.newdoctorvisit.activity.DoctorsNewVisitsActivity
import kotlinx.android.synthetic.main.activity_loctaion_list.*

class LoctaionListActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {




    lateinit var bindingLocationList: ActivityLoctaionListBinding
    lateinit var viewModelLocationList: LocationModelView


    lateinit var drawerLayout: DrawerLayout

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

    }

    override fun onStart() {
        super.onStart()

        getLocationList()
        swiperefresh_locationlist.setOnRefreshListener {
            getLocationList()
        }


    }


    fun getLocationList(){
        viewModelLocationList!!.getLocationList().observe(this, Observer<Locations> {
            it?.let { result ->
                swiperefresh_locationlist.isRefreshing = false
                if(result.locationsStatus){
                    recyclerView_locationlist.adapter = LocationListAdaptor(result.locationsList,this)
                }else{
                    errorAlertDialog(result.locationsNetworkError.errorTitle,result.locationsNetworkError.errorMessage)
                }


            }
        })
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.title) {
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
            "Locations" -> {

            }



        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun addMenuItemInNavMenuDrawer() {

        var menu = nav_view_locationlist.menu
        menu.add("Doctor's Visits")
        menu.add("New Doctor's Visits")
        menu.add("Locations")


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
