package com.zomato.radius

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.zomato.radius.data.FacilityAdapter
import com.zomato.radius.data.FacilityCommunicator
import com.zomato.radius.data.FacilityModel
import com.zomato.radius.data.FacilityModelFactory
import com.zomato.radius.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FacilityCommunicator {
    private var viewModel: FacilityModel? = null
    private lateinit var binding: ActivityMainBinding
    private val facilityAdapter = FacilityAdapter()
    private val exclusionAdapter = FacilityAdapter(true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = FacilityModelFactory().create(FacilityModel::class.java)
        viewModel?.initializeDB(this)
        showLoader()
        setupRv()
        fetchData()
        setupBtn()
    }

    private fun setupBtn() {
        binding.submitBtn.setOnClickListener {
            //maybe a POST API call but for now this
            runSafely {
                showToast(resources.getString(R.string.submitted))
            }
        }
        binding.refreshBtn.setOnClickListener {
            showLoader()
            viewModel?.fetchFacilitiesAndExclusions(true)
        }
    }

    override fun showLoader() {
        binding.progress.visibility = View.VISIBLE
        View.GONE.let {
            binding.rv.visibility = it
            binding.rv1.visibility = it
            binding.submitBtn.visibility = it
            binding.refreshBtn.visibility = it
            binding.exclusion.visibility = it
            binding.select.visibility = it
        }
    }

    override fun hideLoader() {
        binding.progress.visibility = View.GONE
        View.VISIBLE.let {
            binding.rv.visibility = it
            binding.rv1.visibility = it
            binding.submitBtn.visibility = it
            binding.refreshBtn.visibility = it
            binding.exclusion.visibility = it
            binding.select.visibility = it
        }
    }

    private fun setupRv() {
        binding.rv.apply {
            adapter = facilityAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        binding.rv1.apply {
            adapter = exclusionAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

    private fun fetchData() {
        viewModel?.fetchFacilitiesAndExclusions()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel?.apply {
            facilities.observe(this@MainActivity) {
                facilityAdapter.facilities = it
                facilityAdapter.communicator = this@MainActivity
                facilityAdapter.notifyDataSetChanged()
            }
            exclusions.observe(this@MainActivity) {
                exclusionAdapter.exclusions = it
                viewModel?.facilities?.value?.let { facilities -> exclusionAdapter.facilities = facilities }
                exclusionAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun checkButton() {
        var enable = true
        // business logic here
        facilityAdapter.facilities.forEach {
            if (it.id == null || it.selectedOptionId == null || it.id == -1 || it.selectedOptionId == -1) {
                enable = false
                return@forEach
            }
        }
        if (enable) {
            viewModel?.exclusions?.value?.forEach { list ->
                val firstExclusionData = list.getOrNull(0)
                val secondExclusionData = list.getOrNull(1)
                val firstChosenData = facilityAdapter.facilities?.find { it.id == firstExclusionData?.facilityId }
                val secondChosenData = facilityAdapter.facilities?.find { it.id == secondExclusionData?.facilityId }
                if (firstChosenData?.selectedOptionId == firstExclusionData?.optionsId && secondChosenData?.selectedOptionId == secondExclusionData?.optionsId) {
                    enable = false
                    return@forEach
                }
            }
        }
        binding.submitBtn.isEnabled = enable
    }
}