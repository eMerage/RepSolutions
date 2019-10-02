package emerge.projects.repsolutions.ui.doctors.doctorsnew.activity

import android.app.ActivityOptions
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
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
import emerge.projects.repsolutions.databinding.ActivityDoctorNewBinding
import emerge.projects.repsolutions.ui.doctors.doctors.activity.DoctorsActivity
import emerge.projects.repsolutions.ui.doctors.doctorslocationassign.activity.DoctorLocationAssignActivity
import emerge.projects.repsolutions.ui.doctors.doctorsnew.adaptar.AutocompleteDocSpecAdaptor
import emerge.projects.repsolutions.ui.doctors.doctorsnew.adaptar.DuplicateDoctorsAdaptor
import emerge.projects.repsolutions.ui.doctors.doctorsnew.adaptar.SpecializationAdaptor
import emerge.projects.repsolutions.ui.visits.doctorsvisitslist.activity.DoctorsVisitsActivity
import emerge.projects.repsolutions.ui.visits.doctorvisitnew.activity.DoctorsNewVisitsActivity
import emerge.projects.repsolutions.ui.doctors.mvvm.DoctorModelView
import emerge.projects.repsolutions.ui.home.activity.HomeActivity
import emerge.projects.repsolutions.ui.location.locationlist.activity.LoctaionListActivity
import emerge.projects.repsolutions.ui.location.locationnew.activity.LocationNewActivity
import kotlinx.android.synthetic.main.activity_doctor_new.*
import kotlinx.android.synthetic.main.dialog_doctor_duplicate.*

class DoctorNewActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {



    lateinit var bindingNewDoctor: ActivityDoctorNewBinding
    lateinit var viewModelNewDoctor: DoctorModelView

    lateinit var drawerLayout: DrawerLayout

    lateinit var dialogDuplicate: Dialog

    var specList = MutableLiveData<Specialization>()


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

        getDoctorsSpecialization()



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


    fun doctorSaveOnClick(view: View) {
        saveDoctor(specList.value!!.specializationList.filter { it.isSelectSpec} as ArrayList<SpecializationList>,false)
    }


    fun saveDoctor(specList : ArrayList<SpecializationList>,doctorDuplicateStatus : Boolean){
        viewModelNewDoctor.saveDoctors(specList,doctorDuplicateStatus).observe(this, Observer<Doctor> {
            it?.let { result ->
                if (result.doctorsStatus) {

                } else {
                    if(result.isDoctorsDuplicate){
                        doctorsDuplicateListDialog(result.approvedDoctorList)
                    }else{
                        errorAlertDialog(result.networkError)
                    }

                }
            }
        })

    }


    fun doctorsDuplicateListDialog(list : ArrayList<DoctorList>){

        dialogDuplicate = Dialog(this)
        dialogDuplicate.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogDuplicate.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialogDuplicate.setContentView(R.layout.dialog_doctor_duplicate)
        dialogDuplicate.setCancelable(true)
        dialogDuplicate.recyclerView_duplicate_doctors.adapter = DuplicateDoctorsAdaptor(list,this)
        dialogDuplicate.show()


    }




    fun dialogDuplicateDoctorsSaveYesClick(view: View) {
        saveDoctor(specList.value!!.specializationList.filter { it.isSelectSpec} as ArrayList<SpecializationList>,true)
    }

    fun dialogDuplicateDoctorsSaveNoClick(view: View) {
        dialogDuplicate.dismiss()
    }



    fun getDoctorsSpecialization(){
        viewModelNewDoctor!!.getDoctorsSpecialization().observe(this, Observer<Specialization> {
            it?.let { result ->
                if(result.specStatus){
                    specList.value = result
                    var specAdaptor= SpecializationAdaptor(specList.value!!.specializationList,this)
                    recyclerView_doc_spec.adapter = specAdaptor
                    specAdaptor.setOnItemClickListener(object : SpecializationAdaptor.ClickListener {
                        override fun onClick(spec: SpecializationList, aView: View) {
                            specList.value!!.specializationList.sortByDescending { it.isSelectSpec }
                        }
                    })
                    val adapter = AutocompleteDocSpecAdaptor(
                        this,
                        R.layout.textview_autocomplete,
                        specList.value!!.specializationList
                    )
                    autoCompleteTextView_doc_spec.setAdapter(adapter)
                    updateSpecSelect(specAdaptor)

                }else{
                    errorAlertDialog(result.specNetworkError)
                }

            }
        })

    }

    fun updateSpecSelect(adaptor : SpecializationAdaptor){
        viewModelNewDoctor.docSpecItemAddRespons.observe(this, Observer<Boolean> {
            it?.let { result ->
                adaptor.notifyDataSetChanged()
                autoCompleteTextView_doc_spec.setText("")
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
            "New Locations" -> {
                val intentDocVists = Intent(this, LocationNewActivity::class.java)
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

        var menu = nav_view_newdoctor.menu
        menu.add("Dashboard")
        menu.add("Doctor's Visits")
        menu.add("New Doctor's Visits")
        menu.add("Doctors")
        menu.add("Assign Location to Doctors")
        menu.add("Locations")
        menu.add("New Locations")

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
