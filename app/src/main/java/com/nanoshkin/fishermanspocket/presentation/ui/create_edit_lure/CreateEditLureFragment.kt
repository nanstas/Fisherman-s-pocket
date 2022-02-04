package com.nanoshkin.fishermanspocket.presentation.ui.create_edit_lure

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nanoshkin.fishermanspocket.R
import com.nanoshkin.fishermanspocket.databinding.FragmentCreateEditLureBinding
import com.nanoshkin.fishermanspocket.domain.models.LureType
import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.utils.Utils.convertNewsCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateEditLureFragment : Fragment(R.layout.fragment_create_edit_lure) {
    private val viewModel: CreateEditLureViewModel by viewModels()
    private lateinit var binding: FragmentCreateEditLureBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCreateEditLureBinding.bind(view)

        with(binding) {
            val adapter = ArrayAdapter(requireContext(), R.layout.menu_item, LureType.values())
            typeDropMenuAutoCompleteTextView.setAdapter(adapter)

//            typeDropMenuAutoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
//                val selectedItem = parent.getItemAtPosition(position)
//
//            }

            saveButton.setOnClickListener {
                viewModel.save(lureForSave())
            }
        }
    }

    private fun lureForSave(): Lure {
        with(binding) {
            return Lure(
                id = null,
                name = nameEditText.text.toString(),
                manufacturer = manufacturerEditText.text.toString(),
                type = convertNewsCategory(typeDropMenuAutoCompleteTextView.text.toString()),
                divingDepth = deepDropMenuAutoCompleteTextView.text.toString(),
                floatation = floatationDropMenuAutoCompleteTextView.text.toString(),
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