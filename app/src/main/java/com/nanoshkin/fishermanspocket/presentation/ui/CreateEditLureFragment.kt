package com.nanoshkin.fishermanspocket.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nanoshkin.fishermanspocket.R
import com.nanoshkin.fishermanspocket.databinding.FragmentCreateEditLureBinding
import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.domain.models.LureDivingDepth
import com.nanoshkin.fishermanspocket.domain.models.LureFloatation
import com.nanoshkin.fishermanspocket.domain.models.LureType
import com.nanoshkin.fishermanspocket.presentation.viewmodels.LureViewModel
import com.nanoshkin.fishermanspocket.utils.Utils.convertLureDivingDepthCategory
import com.nanoshkin.fishermanspocket.utils.Utils.convertLureFloatationCategory
import com.nanoshkin.fishermanspocket.utils.Utils.convertLureTypeCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateEditLureFragment : Fragment(R.layout.fragment_create_edit_lure) {
    private val viewModel: LureViewModel by viewModels()
    private lateinit var binding: FragmentCreateEditLureBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCreateEditLureBinding.bind(view)

        with(binding) {
            val lureTypeAdapter =
                ArrayAdapter(requireContext(), R.layout.menu_item, LureType.values())
            typeDropMenuAutoCompleteTextView.setAdapter(lureTypeAdapter)

            val lureDeepAdapter =
                ArrayAdapter(requireContext(), R.layout.menu_item, LureDivingDepth.values())
            divingDepthDropMenuAutoCompleteTextView.setAdapter(lureDeepAdapter)

            val lureFloatationAdapter =
                ArrayAdapter(requireContext(), R.layout.menu_item, LureFloatation.values())
            floatationDropMenuAutoCompleteTextView.setAdapter(lureFloatationAdapter)

            saveButton.setOnClickListener {
                viewModel.save(lureForSave())
                findNavController().navigate(R.id.action_createEditLureFragment_to_nav_my_lures)
            }
        }
    }

    private fun lureForSave(): Lure {
        with(binding) {
            return Lure(
                id = null,
                name = nameEditText.text.toString(),
                manufacturer = manufacturerEditText.text.toString(),
                type = convertLureTypeCategory(typeDropMenuAutoCompleteTextView.text.toString()),
                divingDepth = convertLureDivingDepthCategory(divingDepthDropMenuAutoCompleteTextView.text.toString()),
                floatation = convertLureFloatationCategory(floatationDropMenuAutoCompleteTextView.text.toString()),
                weight = weightEditText.text.toString().toIntOrNull(),
                length = lengthEditText.text.toString().toIntOrNull(),
                description = descriptionEditText.text.toString(),
                color = colorEditText.text.toString(),
                effectiveness = caughtFishEditText.text.toString().toIntOrNull(),
                notes = notesEditText.text.toString()
            )
        }
    }
}