package com.nanoshkin.fishermanspocket.presentation.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
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
class CreateEditLureFragment : Fragment() {
    private val photoRequestCode = 1
    private val cameraRequestCode = 2

    private val viewModel: LureViewModel by viewModels()
    private lateinit var binding: FragmentCreateEditLureBinding

    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateEditLureBinding.inflate(inflater, container, false)

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    Toast.makeText(requireContext(), R.string.toast_fill_field_name, Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                viewModel.save(lureForSave())
                findNavController().navigate(R.id.action_createEditLureFragment_to_nav_my_lures)
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
                manufacturer = manufacturerEditText.text.toString(),
                type = convertLureTypeCategory(typeDropMenuAutoCompleteTextView.text.toString()),
                divingDepth = convertLureDivingDepthCategory(divingDepthDropMenuAutoCompleteTextView.text.toString()),
                floatation = convertLureFloatationCategory(floatationDropMenuAutoCompleteTextView.text.toString()),
                weight = weightEditText.text.toString().toIntOrNull(),
                length = lengthEditText.text.toString().toIntOrNull(),
                description = descriptionEditText.text.toString(),
                color = colorEditText.text.toString(),
                effectiveness = caughtFishEditText.text.toString().toIntOrNull(),
                notes = notesEditText.text.toString(),
                imageUrl = uri.toString()
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