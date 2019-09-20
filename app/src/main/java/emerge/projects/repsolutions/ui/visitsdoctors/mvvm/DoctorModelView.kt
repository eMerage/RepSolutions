package emerge.projects.repsolutions.ui.visitsdoctors.mvvm

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import emerge.projects.repsolutions.data.modeldata.*

class DoctorModelView(application: Application) : AndroidViewModel(application) {


    var docsRepository: DoctorRepo = DoctorRepo(application)


    val isVisitsDocListLoading = ObservableField<Boolean>()
    val isVisitsNewDocListLoading = ObservableField<Boolean>()

    var selectedDoctorID = MutableLiveData<Int>()
    var selectedLocationID = MutableLiveData<Int>()
    var selectedProductsList: MutableLiveData<ArrayList<ProductList>> =
        MutableLiveData<ArrayList<ProductList>>()
    var selectedSampleProductsList: MutableLiveData<ArrayList<ProductList>> =
        MutableLiveData<ArrayList<ProductList>>()


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


}