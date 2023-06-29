package com.zomato.radius.data

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FacilityModel: ViewModel() {
    val service = APIService.create()
    private var facilityDB: FacilityDB? = null
    private var facilityDao: FacilityDao? = null
    private var exclusionDB: ExclusionDB? = null
    private var exclusionDao: ExclusionDao? = null
    val facilities: LiveData<List<FacilityData>> get() = _facilites
    private var communicator: FacilityCommunicator? = null
    private val _facilites = MutableLiveData<List<FacilityData>>()

    companion object {
        const val WAIT: Long = 2000
    }

    val exclusions: LiveData<List<List<ExclusionData>>> get() = _exclusions
    private val _exclusions = MutableLiveData<List<List<ExclusionData>>>()
    fun fetchFacilitiesAndExclusions(refresh: Boolean = false) {
        var dbFacilityList: List<FacilityEntity>? = null
        var dbExclusionList: List<ExclusionEntity>? = null
        runBlocking {
            GlobalScope.launch {
                dbFacilityList = facilityDao?.getAll()
                dbExclusionList = exclusionDao?.getAll()
            }
        }
        // we will wait for 1 second if not retrieved from DB by then API call will be made
        Handler(Looper.getMainLooper()).postDelayed({
            if (dbFacilityList.isNullOrEmpty() || dbExclusionList.isNullOrEmpty() || refresh) {
                service.getFacilitiesAndExclusions().enqueue(object : Callback<APIResponse> {
                    override fun onResponse(
                        call: Call<APIResponse>?,
                        response: Response<APIResponse>?
                    ) {
                        communicator?.hideLoader()
                        if (response?.isSuccessful == true) {
                            _facilites.value = response?.body()?.facilities
                            _exclusions.value = response.body()?.exclusions
                            GlobalScope.launch {
                                facilityDao?.deleteAll()
                                exclusionDao?.deleteAll()
                                response?.body()?.facilities?.forEach { facility ->
                                    facilityDao?.insert(
                                        FacilityEntity(
                                            facility.id,
                                            facility.name,
                                            facility.options
                                        )
                                    )
                                }
                                response?.body()?.exclusions?.forEach { exclusion ->
                                    exclusionDao?.insert(ExclusionEntity(pair = exclusion))
                                }
                            }
                        } else {
                            onFailure(null, Throwable("response failed"))
                        }
                    }

                    override fun onFailure(call: Call<APIResponse>?, t: Throwable?) {
                        communicator?.hideLoader()
                        Log.d("api_failed", t?.message.toString())
                    }
                })
            } else {
                communicator?.hideLoader()
                val facilityList = mutableListOf<FacilityData>()
                val exclusionList = mutableListOf<List<ExclusionData>>()
                dbFacilityList?.forEach {
                    facilityList.add(FacilityData(it.id, it.name, it.options))
                }
                dbExclusionList?.forEach { entity ->
                    entity.pair?.let { exclusionList.add(it) }
                }
                _facilites.value = facilityList
                _exclusions.value = exclusionList
            }
        }, WAIT)
    }

    fun initializeDB(context: Context) {
        facilityDB = Room.databaseBuilder(context, FacilityDB::class.java, "facility").build()
        exclusionDB = Room.databaseBuilder(context, ExclusionDB::class.java, "exclusion").build()
        communicator = context as? FacilityCommunicator
        facilityDao = facilityDB?.facilityDao()
        exclusionDao = exclusionDB?.exclusionDao()
    }
}

class FacilityModelFactory :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val vm = when {
            modelClass.isAssignableFrom(FacilityModel::class.java) ->
                FacilityModel()
            else -> null
        }

        if (vm != null) {
            return vm as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}