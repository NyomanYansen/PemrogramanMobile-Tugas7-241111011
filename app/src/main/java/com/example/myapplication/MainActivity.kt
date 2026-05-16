package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.ReminderAdapter
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.repository.ReminderRepository
import com.example.myapplication.viewmodel.ReminderViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ReminderViewModel
    private lateinit var adapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvReminders = findViewById<RecyclerView>(R.id.rvReminders)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        adapter = ReminderAdapter(emptyList())
        rvReminders.adapter = adapter

        // Inisialisasi Arsitektur secara manual
        val repository = ReminderRepository(RetrofitClient.instance)
        viewModel = ReminderViewModel(repository)

        // Hubungkan LiveData dari ViewModel ke UI
        viewModel.reminders.observe(this) { data -> 
            data?.let { adapter.updateData(it) } 
        }
        viewModel.isLoading.observe(this) { loading -> 
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE 
        }
        viewModel.errorMessage.observe(this) { msg -> 
            if (msg != null && msg.isNotEmpty()) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        }

        // Ambil data dari XAMPP
        viewModel.fetchReminders()
    }
}
