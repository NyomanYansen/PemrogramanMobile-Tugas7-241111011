package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Reminder
import com.example.myapplication.repository.ReminderRepository
import kotlinx.coroutines.launch

class ReminderViewModel(private val repository: ReminderRepository) : ViewModel() {

    private val _reminders = MutableLiveData<List<Reminder>>()
    val reminders: LiveData<List<Reminder>> get() = _reminders

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchReminders() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.getReminders()
                _reminders.value = result
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Terjadi kesalahan koneksi"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addReminder(reminder: Reminder) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Gunakan try-catch untuk menangkap error network/parsing
                repository.addReminder(reminder)
                // Jika tidak ada exception, asumsikan berhasil dan refresh
                fetchReminders()
            } catch (e: Exception) {
                // Ini akan memunculkan Toast di Fragment
                _errorMessage.value = "Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
