package emerge.projects.repsolutions.ui.doctors.mvvm

import android.app.Application
import android.view.View
import android.widget.AdapterView
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import emerge.projects.repsolutions.data.modeldata.*

class DoctorModelView(application: Application) : AndroidViewModel(application) {


    var docsRepository: DoctorRepo = DoctorRepo(application)


    val isVisitsDocListLoading = ObservableField<Boolean>()
    val isVisitsNewDocListLoading = ObservableField<Boolean>()
    val isDoctorsListLoading = ObservableField<Boolean>()
    val isNewDoctorLoading = ObservableField<Boolean>()



    var selectedDoctorID = MutableLiveData<Int>()
    var selectedLocationID = MutableLiveData<Int>()
    var selectedProductsList: MutableLiveData<ArrayList<ProductList>> =
        MutableLiveData<ArrayList<ProductList>>()
    var selectedSampleProductsList: MutableLiveData<ArrayList<ProductList>> =
        MutableLiveData<ArrayList<ProductList>>()


    var editTextDoctorName = MutableLiveData<String>()
    var editTextDocContactNumber = MutableLiveData<String>()
    var editTextDocRegNumber = MutableLiveData<String>()
    var editTextDocQualification = MutableLiveData<String>()

    var selectedDoctorSpec = SpecializationList()

    fun getDoctorsVisits(): MutableLiveData<VisitsDoctors> {
        return docsRepository.getDoctorsVisits(isVisitsDocListLoading)
    }

    fun getApprovedDoctors(lat: Double, log: Double): MutableLiveData<Doctor> {
        return docsRepository.getApprovedDoctors(isVisitsNewDocListLoading, lat, log)
    }

    fun getDoctorsLocations(docID: Int, lat: Double, log: Double): MutableLiveData<Locations> {
        return docsRepository.getDoctorsLocations(isVisitsNewDocListLoading, docID, lat, log)
    }

    fun getUsersProducts(): MutableLiveData<Product> {
        return docsRepository.getUsersProducts(isVisitsNewDocListLoading)
    }


    fun getSamplesProducts(): MutableLiveData<Product> {
        return docsRepository.getSampleProducts(isVisitsNewDocListLoading)
    }

    fun setSelectedDoctor(docid : Int) {
        selectedDoctorID.value = docid
    }

    fun setSelectedLocations(locid : Int) {
        selectedLocationID.value = locid
    }


    fun seLoadingStatus(status : Boolean) {
        isVisitsNewDocListLoading.set(status)
    }

    fun getDoctors(): MutableLiveData<Doctor> {
        return docsRepository.getDoctors(isDoctorsListLoading)
    }

    fun searchDoctors(searchName : String,doctorsList : ArrayList<DoctorList>): MutableLiveData<ArrayList<DoctorList>> {
        return docsRepository.searchDoctors(searchName,doctorsList)
    }

    fun onDocSpecItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedDoctorSpec = parent!!.getItemAtPosition(position) as SpecializationList
    }

    fun getDoctorsSpecialization(): MutableLiveData<Specialization> {
        return docsRepository.getDoctorsSpecialization(isNewDoctorLoading)
    }




}