package com.nauk0a.onlineshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nauk0a.onlineshop.databinding.LogInBinding


class LogInFragment : Fragment() {

    private lateinit var binding:LogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = LogInBinding.inflate(inflater, container, false)
        return binding.root
    }


}