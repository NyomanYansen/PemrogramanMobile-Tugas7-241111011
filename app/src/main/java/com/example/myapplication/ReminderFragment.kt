package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentReminderBinding

class ReminderFragment : Fragment() {
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: ReminderRepository
    private lateinit var adapter: ReminderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = ReminderRepository(requireContext())
        setupRecyclerView()
        loadReminders()

        binding.cardAddNote.setOnClickListener {
            showAddReminderDialog()
        }

        binding.fabAddReminder.setOnClickListener {
            showAddReminderDialog()
        }
    }

    private fun setupRecyclerView() {
        adapter = ReminderAdapter(emptyList(), 
            onEditClick = { reminder ->
                Toast.makeText(requireContext(), "Edit: ${reminder.title}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { reminder ->
                showDeleteConfirmation(reminder)
            }
        )
        binding.rvReminders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvReminders.adapter = adapter
    }

    private fun loadReminders() {
        val reminders = repository.getAllReminders()
        adapter.updateData(reminders)
        
        val activeCount = repository.countActiveReminders()
        binding.tvActiveCount.text = "$activeCount AKTIF"
    }

    private fun showDeleteConfirmation(reminder: Reminder) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Pengingat")
            .setMessage("Apakah Anda yakin ingin menghapus pengingat ini?")
            .setPositiveButton("Hapus") { _, _ ->
                repository.deleteReminder(reminder.id)
                loadReminders()
                Toast.makeText(requireContext(), "Pengingat dihapus", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showAddReminderDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_add_reminder, null)

        val etCourseCode = dialogView.findViewById<EditText>(R.id.etCourseCode)
        val etTitle = dialogView.findViewById<EditText>(R.id.etTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
        val etDeadline = dialogView.findViewById<EditText>(R.id.etDeadline)

        AlertDialog.Builder(requireContext())
            .setTitle("Tambah Pengingat Baru")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val courseCode = etCourseCode.text.toString().trim()
                val title = etTitle.text.toString().trim()
                val description = etDescription.text.toString().trim()
                val deadline = etDeadline.text.toString().trim()

                if (courseCode.isEmpty() || title.isEmpty() || deadline.isEmpty()) {
                    Toast.makeText(requireContext(), "Kode MK, Judul, dan Deadline wajib diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val colors = listOf("#3B82F6", "#10B981", "#8B5CF6", "#F59E0B")
                val randomColor = colors.random()

                val newReminder = Reminder(
                    courseCode = courseCode,
                    title = title,
                    description = description,
                    deadline = deadline,
                    color = randomColor
                )
                repository.addReminder(newReminder)
                loadReminders()
                Toast.makeText(requireContext(), "Pengingat ditambahkan", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
