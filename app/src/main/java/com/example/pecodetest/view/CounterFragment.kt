package com.example.pecodetest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pecodetest.databinding.FragmentCounterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CounterFragment : Fragment() {
    private lateinit var binding: FragmentCounterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCounterBinding.inflate(layoutInflater)
        return binding.root
    }
}