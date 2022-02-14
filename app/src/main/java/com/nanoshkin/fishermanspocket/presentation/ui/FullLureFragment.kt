package com.nanoshkin.fishermanspocket.presentation.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.nanoshkin.fishermanspocket.R
import com.nanoshkin.fishermanspocket.databinding.FragmentFullLureBinding
import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.presentation.viewmodels.LureViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FullLureFragment : Fragment(R.layout.fragment_full_lure) {
    private lateinit var binding: FragmentFullLureBinding
    private val viewModel: LureViewModel by viewModels()

    private val lureId: Int by lazy {
        val args by navArgs<FullLureFragmentArgs>()
        args.argLureId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.init(lureId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFullLureBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentLure.collectLatest { lure ->
                renderingUi(lure)

                with(binding) {
                    addNoteMaterialButton.setOnClickListener {
                        addNoteGroup.visibility = View.GONE
                        editNoteGroup.visibility = View.VISIBLE
                        notesEditText.setText(lure.notes)

                        saveNoteMaterialButton.setOnClickListener {
                            viewModel.saveNote(
                                idLure = lureId,
                                note = notesEditText.text.toString()
                            )
                            addNoteGroup.visibility = View.VISIBLE
                            editNoteGroup.visibility = View.GONE
                        }

                        cancelNoteMaterialButton.setOnClickListener {
                            addNoteGroup.visibility = View.VISIBLE
                            editNoteGroup.visibility = View.GONE
                        }
                    }

                    addFishCountMaterialButton.setOnClickListener {
                        viewModel.increaseInCaughtFish(lure)
                    }
                }
            }
        }
    }

    private fun renderingUi(lure: Lure?) {
        if (lure != null) {
            with(binding) {
                nameTextView.text = lure.name
                manufacturerTextView.text = lure.manufacturer ?: "—"
                typeTextView.text = lure.type?.type ?: "—"
                divingDepthTextView.text =
                    if (lure.divingDepth == null || lure.divingDepth.divingDepth == "Unknown") "—" else lure.divingDepth.divingDepth
                lengthTextView.text = lure.length?.toString() ?: "—"
                weightTextView.text = lure.weight?.toString() ?: "—"
                floatationTextView.text =
                    if (lure.floatation == null || lure.floatation.floatation == "Unknown") "—" else lure.floatation.floatation
                colorTextView.text = lure.color ?: "—"
                caughtFishCountTextView.text = lure.effectiveness.toString()
                descriptionTextView.text = lure.description
                noteTextView.text = lure.notes

                Glide.with(lureImageView.context)
                    .load(lure.imageUrl?.toUri())
                    .error(R.drawable.ic_no_photography_24)
                    .timeout(30_000)
                    .into(lureImageView)
            }
        } else {
            findNavController().navigateUp()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                val action =
                    FullLureFragmentDirections.actionFullLureFragmentToEditLureFragment(lureId)
                findNavController().navigate(action)
                true
            }
            R.id.action_remove -> {
                viewModel.removeLure(lureId)
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
