package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        
        val sessionManager = SessionManager(requireContext())
        val username = sessionManager.getUsername()
        
        // Update welcome text with username from session
        binding.tvUsername.text = "Selamat Datang, $username"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
