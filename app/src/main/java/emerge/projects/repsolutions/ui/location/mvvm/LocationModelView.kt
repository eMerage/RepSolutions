package emerge.projects.repsolutions.ui.location.mvvm


import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import emerge.projects.repsolutions.R
import emerge.projects.repsolutions.data.modeldata.District
import emerge.projects.repsolutions.data.modeldata.Locations


class LocationModelView(application: Application) : AndroidViewModel(application) {

    var locationRepository: LocationRepo = LocationRepo(application)

    val isLocationListLoading = ObservableField<Boolean>()
    val isNewLocationLoading = ObservableField<Boolean>()

    var app : Application = application


    var editTextLocationName = MutableLiveData<String>()
    var editTextLocationAddress = MutableLiveData<String>()
    var editTextLocationArea = MutableLiveData<String>()


    var districtList:ArrayAdapter<String>


    init {
        var districtArryList = ArrayList<String>()
        districtList= ArrayAdapter(app, R.layout.list_autocomplete_products,R.id.lbl_name,districtArryList)

    }


    fun getLocationList(): MutableLiveData<Locations> {
        return locationRepository.getLocations(isLocationListLoading)
    }


    fun seLoadingAddingStatus(status : Boolean) {
        isNewLocationLoading.set(status)
    }


    fun getDistrictList(): MutableLiveData<District>  {
        return locationRepository.getDistrict(isLocationListLoading)
    }

    fun setDistrictList(district : District){
        var districtArryList = ArrayList<String>()
        for(item in district.districtList){
            districtArryList.add(item.districtName)
        }
        districtList= ArrayAdapter(app, R.layout.list_autocomplete_products,R.id.lbl_name,districtArryList)
    }



    fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


    }




    /*   fun saveNewLocations(currentLocation: LatLng): MutableLiveData<Locations> {
           return locationRepository.saveNewLocation(isNewLocationLoading,currentLocation,editTextLocationName.value.toString(),)
       }
   */


   /* loding : ObservableField<Boolean>,
    currentLocation: LatLng,
    locName: String,
    locAddress: String,
    locArea: String,
    locTownID: Int,
    locDistrictID: Int,
    locTypeId: Int,
    locationDuplicateStatus : Boolean*/


}