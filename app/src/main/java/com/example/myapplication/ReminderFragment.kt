package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentReminderBinding
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.repository.ReminderRepository as ApiRepository
import com.example.myapplication.viewmodel.ReminderViewModel
import com.example.myapplication.viewmodel.ReminderViewModelFactory

class ReminderFragment : Fragment() {
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: ReminderViewModel
    private lateinit var adapter: ReminderAdapter
    private lateinit var localRepository: ReminderRepository // SQLite repository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localRepository = ReminderRepository(requireContext())
        
        setupViewModel()
        setupRecyclerView()
        setupObservers()

        // Ambil data dari API
        viewModel.fetchReminders()

        binding.cardAddNote.setOnClickListener {
            showAddReminderDialog()
        }

        binding.fabAddReminder.setOnClickListener {
            showAddReminderDialog()
        }
    }

    private fun setupViewModel() {
        val apiService = RetrofitClient.instance
        val apiRepository = ApiRepository(apiService)
        val factory = ReminderViewModelFactory(apiRepository)
        viewModel = ViewModelProvider(this, factory)[ReminderViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = ReminderAdapter(emptyList())
        binding.rvReminders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvReminders.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.reminders.observe(viewLifecycleOwner) { reminders ->
            if (reminders != null) {
                adapter.updateData(reminders)
                binding.tvActiveCount.text = "${reminders.size} TOTAL"
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loadLocalReminders() {
        // Fungsi ini dinonaktifkan agar hanya menampilkan data dari XAMPP
    }

    private fun showAddReminderDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_add_reminder, null)

        val etTitle = dialogView.findViewById<EditText>(R.id.etTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
        val etDate = dialogView.findViewById<EditText>(R.id.etDate)
        val etRemindTime = dialogView.findViewById<EditText>(R.id.etRemindTime)

        AlertDialog.Builder(requireContext())
            .setTitle("Tambah Pengingat Baru")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val title = etTitle.text.toString().trim()
                val description = etDescription.text.toString().trim()
                val date = etDate.text.toString().trim()
                val remindTime = etRemindTime.text.toString().trim()

                if (title.isEmpty() || date.isEmpty() || remindTime.isEmpty()) {
                    Toast.makeText(requireContext(), "Semua field wajib diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val newReminder = Reminder(
                    title = title,
                    description = description,
                    date = date,
                    remindTime = remindTime,
                    isStatus = 1
                )
                
                // Simpan ke Server via ViewModel
                viewModel.addReminder(newReminder)
                
                // Tidak menyimpan ke localRepository lagi
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
