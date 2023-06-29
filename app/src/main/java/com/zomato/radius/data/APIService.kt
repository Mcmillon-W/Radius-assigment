package com.zomato.radius.data

import com.application.services.partner.network.RetrofitManager
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import java.io.Serializable

interface APIService {
    @GET("iranjith4/ad-assignment/db")
    fun getFacilitiesAndExclusions(): Call<APIResponse>

    companion object {
        fun create(): APIService {
            return RetrofitManager.createRetrofitService(
                APIService::class.java
            )
        }
    }
}

data class APIResponse (
    @Expose
    @SerializedName("facilities")
    val facilities: List<FacilityData>? = null,
    @Expose
    @SerializedName("exclusions")
    val exclusions: List<List<ExclusionData>>? = null
): Serializable

data class ExclusionData (
    @Expose
    @SerializedName("facility_id")
    val facilityId: Int? = null,
    @Expose
    @SerializedName("options_id")
    val optionsId: Int? = null
): Serializable

data class FacilityData (
    @Expose
    @SerializedName("facility_id")
    val id: Int? = null,
    @Expose
    @SerializedName("name")
    val name: String? = null,
    @Expose
    @SerializedName("options")
    val options: List<OptionData>? = null,
    var selectedOptionId: Int? = null
    ): Serializable

data class OptionData (
    @Expose
    @SerializedName("name")
    val name: String? = null,
    @Expose
    @SerializedName("id")
    val id: Int? = null,
    @Expose
    @SerializedName("icon")
    val icon: String? = null
): Serializable
