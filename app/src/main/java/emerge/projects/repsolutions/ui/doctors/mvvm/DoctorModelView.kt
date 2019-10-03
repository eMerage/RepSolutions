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
    val isDoctorsAssignLoading = ObservableField<Boolean>()


    val isTextview1Visibale = ObservableField<Boolean>()
    val isTextViewDoctorsVisibale = ObservableField<Boolean>()

    val isTextview2Visibale = ObservableField<Boolean>()
    val isTextViewLocationsVisibale = ObservableField<Boolean>()


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


    var specList = MutableLiveData<Specialization>()
    var docSpecItemAddRespons = MutableLiveData<Boolean>()


    var aprovedDocList = MutableLiveData<Doctor>()
    var docItemAddRespons = MutableLiveData<Boolean>()
    var selectedDoctor = DoctorList()


    var aprovedLocationList = MutableLiveData<Locations>()
    var locationItemAddRespons = MutableLiveData<Boolean>()
    var selectedLocation = LocationsList()


    val isUpdateDoctorsLoading = ObservableField<Boolean>()


    init {
        isTextview1Visibale.set(true)
        isTextViewDoctorsVisibale.set(false)

        isTextview2Visibale.set(true)
        isTextViewLocationsVisibale.set(false)

    }


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

    fun setSelectedDoctor(docid: Int) {
        selectedDoctorID.value = docid
    }

    fun setSelectedLocations(locid: Int) {
        selectedLocationID.value = locid
    }


    fun seLoadingStatus(status: Boolean) {
        isVisitsNewDocListLoading.set(status)
    }

    fun getDoctors(): MutableLiveData<Doctor> {
        return docsRepository.getDoctors(isDoctorsListLoading)
    }

    fun searchDoctors(
        searchName: String,
        doctorsList: ArrayList<DoctorList>
    ): MutableLiveData<ArrayList<DoctorList>> {
        return docsRepository.searchDoctors(searchName, doctorsList)
    }

    fun onDocSpecItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedDoctorSpec = parent!!.getItemAtPosition(position) as SpecializationList
        addSpecFromItemSelect(selectedDoctorSpec)

    }

    fun getDoctorsSpecialization(): MutableLiveData<Specialization> {
        specList = docsRepository.getDoctorsSpecialization(isNewDoctorLoading)
        return specList
    }


    fun addSpecFromItemSelect(spec: SpecializationList) {
        for (item in specList.value!!.specializationList) {
            if (item.specID == spec.specID)
                item.isSelectSpec = true

        }
        specList.value!!.specializationList.sortByDescending { it.isSelectSpec }
        docSpecItemAddRespons.value = true

    }


    fun saveDoctors(
        specList: ArrayList<SpecializationList>,
        doctorDuplicateStatus: Boolean
    ): MutableLiveData<Doctor> {
        return docsRepository.saveNewDoctor(
            isNewDoctorLoading,
            editTextDoctorName.value.toString(),
            editTextDocContactNumber.value.toString(),
            editTextDocRegNumber.value.toString(),
            editTextDocQualification.value.toString(), specList, doctorDuplicateStatus
        )
    }


    fun getApprovedDoctors(): MutableLiveData<Doctor> {
        aprovedDocList = docsRepository.getApprovedDoctors(isDoctorsAssignLoading)
        return aprovedDocList
    }


    fun onClickDoctorSerach() {
        isTextview1Visibale.set(false)
        isTextViewDoctorsVisibale.set(true)
    }


    fun afterItemClickDoctorSerach() {
        isTextview1Visibale.set(true)
        isTextViewDoctorsVisibale.set(false)
    }


    fun onDoctorsItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedDoctor = parent!!.getItemAtPosition(position) as DoctorList
        addDocFromItemSelect(selectedDoctor)

    }

    fun addDocFromItemSelect(doc: DoctorList) {
        for (item in aprovedDocList.value!!.approvedDoctorList) {
            if (item.doctorID == doc.doctorID)
                item.isSelect = true

        }
        aprovedDocList.value!!.approvedDoctorList.sortByDescending { it.isSelect }
        docItemAddRespons.value = true
        afterItemClickDoctorSerach()
    }


    fun getApprovedLocations(): MutableLiveData<Locations> {
        aprovedLocationList = docsRepository.getApprovedLocation(isDoctorsAssignLoading)
        return aprovedLocationList
    }


    fun onClickLocationSerach() {
        isTextview2Visibale.set(false)
        isTextViewLocationsVisibale.set(true)
    }


    fun afterItemClickLocationSerach() {
        isTextview2Visibale.set(true)
        isTextViewLocationsVisibale.set(false)
    }

    fun onLocationsItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedLocation = parent!!.getItemAtPosition(position) as LocationsList
        addLocationFromItemSelect(selectedLocation)

    }


    fun addLocationFromItemSelect(loc: LocationsList) {


        loc.isSelect = !loc.isSelect

        aprovedLocationList.value!!.locationsList.sortByDescending { it.isSelect }
        locationItemAddRespons.value = true

        afterItemClickLocationSerach()
    }


    fun assignDoctorsToLocation(): MutableLiveData<Doctor> {
        return  docsRepository.assignDoctorsToLocation(isDoctorsListLoading,  aprovedDocList.value!!.approvedDoctorList,  aprovedLocationList.value!!.locationsList)
    }


}