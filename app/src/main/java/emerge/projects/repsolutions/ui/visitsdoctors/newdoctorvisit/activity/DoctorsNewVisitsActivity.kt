package emerge.projects.repsolutions.ui.visitsdoctors.newdoctorvisit.activity

import android.Manifest
import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
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
import com.aminography.choosephotohelper.ChoosePhotoHelper
import com.aminography.choosephotohelper.callback.ChoosePhotoCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.navigation.NavigationView
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.*
import emerge.projects.repsolutions.databinding.ActivityDoctorsNewVisitsBinding

import emerge.projects.repsolutions.ui.location.locationlist.activity.LoctaionListActivity
import emerge.projects.repsolutions.ui.location.newlocation.activity.LocationNewActivity
import emerge.projects.repsolutions.ui.visitsdoctors.doctorsvisitslist.activity.DoctorsVisitsActivity
import emerge.projects.repsolutions.ui.visitsdoctors.mvvm.DoctorModelView
import emerge.projects.repsolutions.ui.visitsdoctors.newdoctorvisit.adaptor.*
import kotlinx.android.synthetic.main.activity_doctors_new_visits.*

class DoctorsNewVisitsActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener  {




    lateinit var bindingNewDoctorVisits: ActivityDoctorsNewVisitsBinding
    lateinit var viewModelNewDoctorVisits: DoctorModelView


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    lateinit var locationRequest: LocationRequest

    val LOCATION_REQUEST = 900
    private val REQUEST_CHECK_SETTINGS = 2

     var currentLocation: LatLng = LatLng(0.0,0.0)

    lateinit var drawerLayout: DrawerLayout


    private lateinit var choosePhotoHelper: ChoosePhotoHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        bindingNewDoctorVisits = DataBindingUtil.setContentView(this, R.layout.activity_doctors_new_visits)
        bindingNewDoctorVisits.lifecycleOwner = this
        viewModelNewDoctorVisits = ViewModelProviders.of(this).get(DoctorModelView::class.java)
        bindingNewDoctorVisits.newdoctorsVistis = viewModelNewDoctorVisits



        val toolbar: Toolbar = findViewById(R.id.toolbar_newdocvisits)
        setSupportActionBar(toolbar)


        drawerLayout = findViewById(R.id.drawer_layout_newdocvisits)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view_newdocvisits)
        navView.setNavigationItemSelectedListener(this)

        addMenuItemInNavMenuDrawer()


    }

    override fun onStart() {
        super.onStart()


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                makeRequest()
            }else{
                createLocationRequest()
            }
        }else{
            createLocationRequest()
        }



        choosePhotoHelper = ChoosePhotoHelper.with(this)
            .asFilePath()
            .build(ChoosePhotoCallback {
                Glide.with(this)
                    .load(it)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_photo_camera_black_24dp))
                    .into(imageView_taken_photo)
            })



        autoCompleteTextView_doctors.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                var selectedDoctor: DoctorList = parent.getItemAtPosition(position) as DoctorList

            }


        autoCompleteTextView_products.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                var selectedProduct: ProductList = parent.getItemAtPosition(position) as ProductList

            }


        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                var mLastLocation: Location = locationResult.lastLocation
                viewModelNewDoctorVisits.seLoadingStatus(false)
                getDoctorsListToVisits(mLastLocation.latitude,mLastLocation.longitude)
            }
        }



    }


    fun getDoctorsListToVisits(lat : Double , log : Double){
        viewModelNewDoctorVisits!!.getApprovedDoctors(lat,log).observe(this, Observer<Doctor> {
            it?.let { result ->
                if(result.doctorsStatus){
                    var docAdapter =  DoctorsAdaptor(result.approvedDoctorList,this)
                    recyclerView_newdocvisits.adapter =docAdapter
                    docAdapter.setOnItemClickListener(object : DoctorsAdaptor.ClickListener {
                        override fun onClick(doctor: DoctorList, aView: View) {
                            getDoctorsLocationListToVisits(doctor.doctorID)
                            getProducts()
                            getSamplesProducts()

                        }
                    })
                    autoCompleteTextView_doctors.setAdapter(AutoCompleteDoctorsAdapter(this, R.layout.activity_doctors_new_visits, R.id.lbl_name, result.approvedDoctorList))
                }else{
                    errorAlertDialog(result.networkError.errorTitle,result.networkError.errorMessage)
                }
            }
        })
    }


    fun getDoctorsLocationListToVisits(docID : Int){
        viewModelNewDoctorVisits!!.getDoctorsLocations(docID,currentLocation.latitude,currentLocation.longitude).observe(this, Observer<Locations> {
            it?.let { result ->
                if(result.locationsStatus){
                    var docLocationAdapter =  DoctorsLocatonsAdaptor(result.locationsList,this)
                    recyclerView_doclocations.adapter =docLocationAdapter
                    docLocationAdapter.setOnItemClickListener(object : DoctorsLocatonsAdaptor.ClickListener {
                        override fun onClick(locations: LocationsList, aView: View) {


                        }
                    })
                }else{
                    errorAlertDialog(result.locationsNetworkError.errorTitle,result.locationsNetworkError.errorMessage)
                }
            }
        })

    }

    fun getProducts(){
        viewModelNewDoctorVisits!!.getUsersProducts().observe(this, Observer<Product> {
            it?.let { result ->
                if(result.productStatus){
                    var productsAdapter =  VisitsProductsAdaptor(result.productList,this)
                    recyclerView_docvistsproducts.adapter =productsAdapter

                    productsAdapter.setOnItemClickListener(object : VisitsProductsAdaptor.ClickListener {
                        override fun onClick(product: ProductList, aView: View) {

                        }
                    })

                    autoCompleteTextView_products.setAdapter(AutoCompleteProductsAdapter(this, R.layout.activity_doctors_new_visits, R.id.lbl_name, result.productList))
                }else{
                    errorAlertDialog(result.productNetworkError.errorTitle,result.productNetworkError.errorMessage)
                }
            }
        })

    }



    fun getSamplesProducts(){
        viewModelNewDoctorVisits!!.getSamplesProducts().observe(this, Observer<Product> {
            it?.let { result ->
                if(result.productStatus){
                    var sampleProductsAdapter =  VisitsSampleProductsAdaptor(result.productList,this)
                    recyclerView_docvistspromoitems.adapter =sampleProductsAdapter

                    sampleProductsAdapter.setOnItemClickListener(object : VisitsSampleProductsAdaptor.ClickListener {
                        override fun onClick(product: ProductList, aView: View) {

                        }
                    })

                }else{
                    errorAlertDialog(result.productNetworkError.errorTitle,result.productNetworkError.errorMessage)
                }
            }
        })

    }






    fun captureImageIconClick(view : View){
        choosePhotoHelper.showChooser()

    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun makeRequest() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        choosePhotoHelper.onActivityResult(requestCode, resultCode, data)
    }



    fun createLocationRequest() {

        viewModelNewDoctorVisits.seLoadingStatus(true)

        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client =  LocationServices.getSettingsClient(this)

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
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)


    }
    private fun onLocationChanged(location: Location) {
        currentLocation = LatLng(location.latitude, location.longitude)

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
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


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Doctor's Visits" -> {
                val intentDocVists = Intent(this, DoctorsVisitsActivity::class.java)
                val bndlanimationDocVists = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }

            "New Doctor's Visits" -> {

            }

            "Locations" -> {
                val intentDocVists = Intent(this, LoctaionListActivity::class.java)
                val bndlanimationDocVists = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }
            "Locations" -> {
                val intentDocVists = Intent(this, LocationNewActivity::class.java)
                val bndlanimationDocVists = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
                startActivity(intentDocVists, bndlanimationDocVists)
                this.finish()
            }


        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun addMenuItemInNavMenuDrawer() {

        var menu = nav_view_newdocvisits.menu
        menu.add("Doctor's Visits")
        menu.add("New Doctor's Visits")
        menu.add("Locations")
        menu.add("New Locations")
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
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                        makeRequest()
                    }else{
                        createLocationRequest()
                    }
                }else{
                    createLocationRequest()
                }

                return true
            }

        }
        return super.onOptionsItemSelected(item)

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
