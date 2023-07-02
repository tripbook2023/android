package com.tripbook.tripbook.views.login.profile

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentAdditionalBinding
import com.tripbook.tripbook.viewmodel.AdditionalProfileViewModel

class AdditionalFragment :
    BaseFragment<FragmentAdditionalBinding, AdditionalProfileViewModel>(R.layout.fragment_additional) {

    override val viewModel: AdditionalProfileViewModel by activityViewModels()

    override fun init() {
        binding.viewModel = viewModel
        setupSpinner()
        setupSpinnerHandler()
        binding.completeButton.setOnClickListener {
            // 서버에 전체 회원가입 정보 전송
        }
    }

    private fun setupSpinner() {
        val year = resources.getStringArray(R.array.year)
        val month = resources.getStringArray(R.array.month)
        val day = resources.getStringArray(R.array.day)

        val yearAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, year)
        binding.spinnerYear.adapter = yearAdapter

        val monthAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, month)
        binding.spinnerMonth.adapter = monthAdapter

        val dayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, day)
        binding.spinnerDay.adapter = dayAdapter
    }

    private val spinnerListener = object : OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            if (pos > 0) viewModel.setBirth(checkSpinner(parent!!.id))
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }

    private fun setupSpinnerHandler() {
        binding.spinnerYear.onItemSelectedListener = spinnerListener
        binding.spinnerMonth.onItemSelectedListener = spinnerListener
        binding.spinnerDay.onItemSelectedListener = spinnerListener
    }

    fun checkSpinner(id: Int): String {
        return when (id) {
            R.id.spinner_year -> "year"
            R.id.spinner_month -> "month"
            else -> "day"
        }
    }

}
