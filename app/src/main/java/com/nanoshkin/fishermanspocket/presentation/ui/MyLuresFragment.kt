package com.nanoshkin.fishermanspocket.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nanoshkin.fishermanspocket.R
import com.nanoshkin.fishermanspocket.adapter.LureListAdapter
import com.nanoshkin.fishermanspocket.databinding.FragmentMyLuresBinding
import com.nanoshkin.fishermanspocket.presentation.viewmodels.LureViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MyLuresFragment : Fragment(R.layout.fragment_my_lures) {
    private val viewModel: LureViewModel by viewModels()
    private lateinit var binding: FragmentMyLuresBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMyLuresBinding.bind(view)

        val adapter = LureListAdapter()
        binding.lureListRecyclerView.adapter = adapter

//        viewModel.dataLures.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//        }
        lifecycleScope.launchWhenStarted {
            viewModel.dataLures.collectLatest {
                adapter.submitList(it)
            }
        }

    }
}