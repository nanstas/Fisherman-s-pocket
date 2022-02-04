package com.nanoshkin.fishermanspocket.presentation.ui.lures

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nanoshkin.fishermanspocket.R
import com.nanoshkin.fishermanspocket.adapter.LureListAdapter
import com.nanoshkin.fishermanspocket.databinding.FragmentMyLuresBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyLuresFragment : Fragment(R.layout.fragment_my_lures) {
    private val viewModel: MyLuresViewModel by viewModels()
    private lateinit var binding: FragmentMyLuresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onRefresh()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMyLuresBinding.bind(view)

        val adapter = LureListAdapter()
        binding.lureListRecyclerView.adapter = adapter

        viewModel.dataLures.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }
}