package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentScheduleBinding
import com.google.android.material.button.MaterialButton

class ScheduleFragment : Fragment(R.layout.fragment_schedule) {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: CourseRepository
    private lateinit var adapter: ScheduleAdapter
    private var selectedDay: String = "SENIN"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentScheduleBinding.bind(view)
        
        repository = CourseRepository(requireContext())
        setupRecyclerView()
        setupDaySelector()
        
        loadSchedule("SENIN") // Default to Monday
    }

    private fun setupRecyclerView() {
        adapter = ScheduleAdapter(emptyList())
        binding.rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSchedule.adapter = adapter
    }

    private fun setupDaySelector() {
        val dayButtons = mapOf(
            "SENIN" to binding.btnSen,
            "SELASA" to binding.btnSel,
            "RABU" to binding.btnRab,
            "KAMIS" to binding.btnKam,
            "JUMAT" to binding.btnJum
        )

        dayButtons.forEach { (day, button) ->
            button.setOnClickListener {
                updateButtonStyles(day, dayButtons)
                loadSchedule(day)
            }
        }
    }

    private fun updateButtonStyles(activeDay: String, buttons: Map<String, MaterialButton>) {
        buttons.forEach { (day, button) ->
            if (day == activeDay) {
                button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.navy_blue))
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                // Change to Unelevated style logic
                button.setStrokeWidth(0)
            } else {
                button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), android.R.color.transparent))
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
                button.setStrokeColor(ContextCompat.getColorStateList(requireContext(), R.color.light_gray_accent))
                button.setStrokeWidth(2)
            }
        }
    }

    private fun loadSchedule(day: String) {
        val courses = repository.getCoursesByDay(day)
        val items = mutableListOf<ScheduleItem>()
        
        if (courses.isNotEmpty()) {
            items.add(ScheduleItem.Header(day))
            courses.forEach { items.add(ScheduleItem.Content(it)) }
        }
        
        adapter.updateData(items)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
