package emerge.projects.repsolutions.ui.location.locationnew.activity

import android.Manifest
import android.app.ActivityOptions
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Rect
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.*
import emerge.projects.repsolutions.databinding.ActivityLocationNewBinding
import emerge.projects.repsolutions.ui.doctors.doctors.activity.DoctorsActivity
import emerge.projects.repsolutions.ui.doctors.doctorslocationassign.activity.DoctorLocationAssignActivity
import emerge.projects.repsolutions.ui.doctors.doctorsnew.activity.DoctorNewActivity

import emerge.projects.repsolutions.ui.location.locationlist.activity.LoctaionListActivity
import emerge.projects.repsolutions.ui.location.locationnew.adaptar.AutocompleteDistrictAdaptor
import emerge.projects.repsolutions.ui.location.mvvm.LocationModelView
import emerge.projects.repsolutions.ui.location.locationnew.adaptar.AutocompleteTownAdaptor
import emerge.projects.repsolutions.ui.location.locationnew.adaptar.DuplicateLocationsAdaptor
import emerge.projects.repsolutions.ui.location.locationnew.adaptar.SpinnerLocationTypeAdaptor
import emerge.projects.repsolutions.ui.visits.doctorsvisitslist.activity.DoctorsVisitsActivity
import emerge.projects.repsolutions.ui.visits.doctorvisitnew.activity.DoctorsNewVisitsActivity
import emerge.projects.repsolutions.ui.home.activity.HomeActivity
import kotlinx.android.synthetic.main.activity_location_new.*
import kotlinx.android.synthetic.main.dialog_location_duplicate.*


class LocationNewActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    OnMapReadyCallback {


    lateinit var bindingNewLocation: ActivityLocationNewBinding
    lateinit var viewModelNewLocation: LocationModelView

    lateinit var drawerLayout: DrawerLayout


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    lateinit var locationRequest: LocationRequest

    val LOCATION_REQUEST = 900
    private val REQUEST_CHECK_SETTINGS = 2

    var currentLocation: LatLng = LatLng(0.0, 0.0)

    private lateinit var mMap: GoogleMap

    lateinit var dialogDuplicate: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingNewLocation = DataBindingUtil.setContentView(this, R.layout.activity_location_new)
        bindingNewLocation.lifecycleOwner = this
        viewModelNewLocation = ViewModelProviders.of(this).get(LocationModelView::class.java)
        bindingNewLocation.newlocations = viewModelNewLocation


        val toolbar: Toolbar = findViewById(R.id.toolbar_newlocationlist)
        setSupportActionBar(toolbar)


        drawerLayout = findViewById(R.id.drawer_layout_newlocationlist)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view_newlocationlist)
        navView.setNavigationItemSelectedListener(this)

        addMenuItemInNavMenuDrawer()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mapView_addnewlocation.onCreate(savedInstanceState)


    }

    override fun onStart() {
        super.onStart()

        mapView_addnewlocation.onStart()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_CALENDAR
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                makeRequest()
            } else {
                createLocationRequest()
            }
        } else {
            createLocationRequest()
        }


        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {

                viewModelNewLocation.seLoadingAddingStatus(false)


                var mLastLocation: Location = locationResult.lastLocation

                currentLocation = LatLng(mLastLocation.latitude, mLastLocation.longitude)

                if (mapView_addnewlocation != null) {
                    mapView_addnewlocation.getMapAsync(this@LocationNewActivity)
                }


            }
        }

        keyboardListener()

        getDistricts()
        getTowns()
        getAreas()
        getLocationTypes()
    }

    override fun isDestroyed(): Boolean {
        mapView_addnewlocation.onDestroy()
        return super.isDestroyed()

    }

    override fun onStop() {
        super.onStop()
        mapView_addnewlocation.onStop()

    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView_addnewlocation.onLowMemory()
    }

    override fun onResume() {
        super.onResume()
        mapView_addnewlocation.onResume()
    }


    private fun getDistricts() {
        viewModelNewLocation.getDistrictList().observe(this, Observer<District> {
            it?.let { result ->
                if (result.districtStatus) {
                    val adapter = AutocompleteDistrictAdaptor(
                        this,
                        R.layout.textview_autocomplete,
                        result.districtList
                    )
                    autoCompleteTextView_location_district.setAdapter(adapter)
                } else {
                    errorAlertDialogRetry(result.districtNetworkError, 1)
                }
            }
        })
    }

    private fun getTowns() {
        viewModelNewLocation.getTownList().observe(this, Observer<Town> {
            it?.let { result ->
                if (result.townStatus) {
                    val adapter = AutocompleteTownAdaptor(
                        this,
                        R.layout.textview_autocomplete,
                        result.townList
                    )
                    autoCompleteTextView_location_town.setAdapter(adapter)
                } else {
                    errorAlertDialogRetry(result.townNetworkError, 2)
                }
            }
        })
    }


    private fun getAreas() {
        viewModelNewLocation.getAreaList().observe(this, Observer<Area> {
            it?.let { result ->
                if (result.areaStatus) {
                    val adapter = ArrayAdapter(
                        this,
                        R.layout.textview_autocomplete,
                        result.areaList
                    )
                    autoCompleteTextView_location_area.setAdapter(adapter)
                } else {
                    errorAlertDialogRetry(result.areaNetworkError, 3)
                }
            }
        })
    }


    private fun getLocationTypes(){
        viewModelNewLocation.getLocationTypeList().observe(this, Observer<LocationsTyps> {
            it?.let { result ->
                if (result.locationsTypeStatus) {
                    val adapter = SpinnerLocationTypeAdaptor(
                        this,
                        result.locationsTypeList
                    )
                    spinner_location_type.adapter = adapter
                } else {
                    errorAlertDialogRetry(result.locationsTypeNetworkError, 4)
                }
            }
        })

    }



    private fun saveLocation(dupliacteStatus : Boolean){
        viewModelNewLocation.saveNewLocations(currentLocation,dupliacteStatus).observe(this, Observer<Locations> {
            it?.let { result ->
                if (result.locationsStatus) {

                } else {
                    if(result.isLocationsDuplicate){
                        locationDuplicateListDialog(result.locationsList)
                    }else{
                        errorAlertDialog(result.locationsNetworkError)
                    }

                }
            }
        })

    }


    fun locationSaveOnClick(view: View) {
        saveLocation(false)
    }

    fun locationDuplicateListDialog(list : ArrayList<LocationsList>){

        dialogDuplicate = Dialog(this)
        dialogDuplicate.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogDuplicate.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialogDuplicate.setContentView(R.layout.dialog_location_duplicate)
        dialogDuplicate.setCancelable(true)
        dialogDuplicate.recyclerView_duplicate_location.adapter = DuplicateLocationsAdaptor(list,this)
        dialogDuplicate.show()

    }


    fun dialogDuplicateLocationSaveYesClick(view: View) {
        saveLocation(true)
    }

    fun dialogDuplicateLocationSaveNoClick(view: View) {
        dialogDuplicate.dismiss()
    }


    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!

        mMap.uiSettings.isMyLocationButtonEnabled = false
        mMap.uiSettings.setAllGesturesEnabled(true)
        mMap.uiSettings.isMapToolbarEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map))

        if (currentLocation == null) {
            createLocationRequest()
        } else {
            mMap.addMarker(MarkerOptions().position(currentLocation).title("").draggable(true))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17f))
        }

        mMap.setOnCameraIdleListener {
            if (mMap == null) {
            } else {
                mMap.clear()
                currentLocation = mMap.cameraPosition.target

                mMap.addMarker(MarkerOptions().position(currentLocation).title("").draggable(true))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17f))
            }

        }

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
            "Doctors" -> {
                val intentDocVists = Intent(this, DoctorsActivity::class.java)
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

        var menu = nav_view_newlocationlist.menu
        menu.add("Dashboard")
        menu.add("Doctor's Visits")
        menu.add("New Doctor's Visits")
        menu.add("Doctors")
        menu.add("New Doctor")
        menu.add("Assign Location to Doctors")
        menu.add("Locations")

        //  menu.add(0, MENU_EDIT, Menu.NONE, R.string.itemName).setIcon(R.drawable.itemDrawable);

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_location_refrash_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_locationupdate -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_CALENDAR
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        makeRequest()
                    } else {
                        createLocationRequest()
                    }
                } else {
                    createLocationRequest()
                }

                return true
            }

            R.id.action_location_data_refresh -> {
                getDistricts()
                return true
            }

        }
        return super.onOptionsItemSelected(item)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun makeRequest() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST)
    }

    fun createLocationRequest() {

        viewModelNewLocation.seLoadingAddingStatus(true)

        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)

        val task = client?.checkLocationSettings(builder.build())
        builder.setAlwaysShow(true)

        task?.addOnSuccessListener {
            startLocationUpdates()

        }
        task?.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)

                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_REQUEST -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    createLocationRequest()
                } else {
                    Toast.makeText(this, "Oops! Permission Denied!!", Toast.LENGTH_SHORT).show()
                }
                return
            }

        }
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


    fun errorAlertDialogRetry(networkError: NetworkError, code: Int) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(networkError.errorTitle)
        alertDialogBuilder.setMessage(networkError.errorMessage)
        alertDialogBuilder.setPositiveButton(
            "Re-Try"
        ) { _, _ ->
            when (code) {
                1 -> getDistricts()
                2 -> getTowns()
                3 -> getAreas()
                4 -> getLocationTypes()
            }


        }
        alertDialogBuilder.setNegativeButton(
            "NO",
            DialogInterface.OnClickListener { _, _ -> return@OnClickListener })
        alertDialogBuilder.show()
    }


    fun keyboardListener() {
        drawer_layout_newlocationlist.viewTreeObserver.addOnGlobalLayoutListener {
            val rec = Rect()
            drawer_layout_newlocationlist.getWindowVisibleDisplayFrame(rec)
            val screenHeight = drawer_layout_newlocationlist.rootView.height
            val keypadHeight = screenHeight - rec.bottom
            if (keypadHeight > screenHeight * 0.15) {
                mapView_addnewlocation.visibility = View.GONE
            } else {
                mapView_addnewlocation.visibility = View.VISIBLE
            }
        }
    }

}
