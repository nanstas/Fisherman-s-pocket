package com.nanoshkin.fishermanspocket.presentation.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.nanoshkin.fishermanspocket.R
import com.nanoshkin.fishermanspocket.databinding.FragmentCreateLureBinding
import com.nanoshkin.fishermanspocket.domain.models.lure.Lure
import com.nanoshkin.fishermanspocket.domain.models.lure.LureDivingDepth
import com.nanoshkin.fishermanspocket.domain.models.lure.LureFloatation
import com.nanoshkin.fishermanspocket.domain.models.lure.LureType
import com.nanoshkin.fishermanspocket.presentation.viewmodels.LureViewModel
import com.nanoshkin.fishermanspocket.utils.Utils.convertLureDivingDepthCategory
import com.nanoshkin.fishermanspocket.utils.Utils.convertLureFloatationCategory
import com.nanoshkin.fishermanspocket.utils.Utils.convertLureTypeCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateLureFragment : Fragment(R.layout.fragment_create_lure) {
    private val photoRequestCode = 1
    private val cameraRequestCode = 2

    private val viewModel: LureViewModel by viewModels()
    private lateinit var binding: FragmentCreateLureBinding

    private var uri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCreateLureBinding.bind(view)

        binding.pickImageButton.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(2048)
                .galleryOnly()
                .galleryMimeTypes(
                    arrayOf(
                        "image/png",
                        "image/jpeg",
                    )
                )
                .start(photoRequestCode)
        }

        binding.takePhotoButton.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(2048)
                .cameraOnly()
                .start(cameraRequestCode)
        }

        viewModel.currentLureImage.observe(viewLifecycleOwner) { uri ->
            if (uri == null) {
                binding.currentLurePhotoGroup.visibility = View.GONE
                return@observe
            }
            binding.currentLurePhotoGroup.visibility = View.VISIBLE
            binding.currentLureImageView.setImageURI(uri)
        }

        binding.removeCurrentLureImageButton.setOnClickListener {
            viewModel.removeCurrentLureImage()
            uri = null
        }

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
                if (nameEditText.text.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        R.string.toast_fill_field_name,
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }

                viewModel.save(lureForSave())
                findNavController().navigateUp()
            }

            cancelButton.setOnClickListener {
                val activity = activity ?: return@setOnClickListener
                val dialog = AlertDialog.Builder(activity)
                dialog.setMessage(R.string.alert_dialog_cancellation)
                    .setPositiveButton(R.string.ok) { alertDialog, _ ->
                        alertDialog.dismiss()
                        findNavController().navigateUp()
                    }
                    .setNegativeButton(R.string.cancel) { alertDialog, _ ->
                        alertDialog.cancel()
                    }
                    .create()
                    .show()
            }
        }
    }

    private fun lureForSave(): Lure {
        with(binding) {
            return Lure(
                id = null,
                name = nameEditText.text.toString(),
                manufacturer = if (manufacturerEditText.text.isNullOrEmpty()) null else manufacturerEditText.text.toString(),
                type = convertLureTypeCategory(typeDropMenuAutoCompleteTextView.text.toString()),
                divingDepth = convertLureDivingDepthCategory(divingDepthDropMenuAutoCompleteTextView.text.toString()),
                floatation = convertLureFloatationCategory(floatationDropMenuAutoCompleteTextView.text.toString()),
                weight = weightEditText.text.toString().toIntOrNull(),
                length = lengthEditText.text.toString().toIntOrNull(),
                description = descriptionEditText.text.toString(),
                color = if (colorEditText.text.isNullOrEmpty()) null else colorEditText.text.toString(),
                effectiveness = if (caughtFishEditText.text.isNullOrEmpty()) 0 else caughtFishEditText.text.toString().toInt(),
                notes = notesEditText.text.toString(),
                imageUrl = if (uri == null) null else uri.toString()
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            return
        }
        if (resultCode == Activity.RESULT_OK && requestCode == photoRequestCode || resultCode == Activity.RESULT_OK && requestCode == cameraRequestCode) {
            uri = data?.data
            viewModel.changeCurrentLureImage(uri)
            return
        }
    }
}