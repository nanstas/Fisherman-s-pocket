package com.nanoshkin.fishermanspocket.presentation.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.dhaval2404.imagepicker.ImagePicker
import com.nanoshkin.fishermanspocket.R
import com.nanoshkin.fishermanspocket.databinding.FragmentEditLureBinding
import com.nanoshkin.fishermanspocket.domain.models.lure.Lure
import com.nanoshkin.fishermanspocket.domain.models.lure.LureDivingDepth
import com.nanoshkin.fishermanspocket.domain.models.lure.LureFloatation
import com.nanoshkin.fishermanspocket.domain.models.lure.LureType
import com.nanoshkin.fishermanspocket.presentation.viewmodels.LureViewModel
import com.nanoshkin.fishermanspocket.utils.Utils.convertLureDivingDepthCategory
import com.nanoshkin.fishermanspocket.utils.Utils.convertLureFloatationCategory
import com.nanoshkin.fishermanspocket.utils.Utils.convertLureTypeCategory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EditLureFragment : Fragment(R.layout.fragment_edit_lure) {
    private val photoRequestCode = 1
    private val cameraRequestCode = 2

    private val viewModel: LureViewModel by viewModels()
    private lateinit var binding: FragmentEditLureBinding

    private val lureId: Int by lazy {
        val args by navArgs<EditLureFragmentArgs>()
        args.lureIdArg
    }

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(lureId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEditLureBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentLure.collectLatest { lure ->

                with(binding) {
                    nameEditText.setText(lure.name)
                    lure.manufacturer.let { manufacturerEditText.setText(it) }
                    if (lure.type == null || lure.type.name == "OTHER") typeDropMenuAutoCompleteTextView.setText(
                        ""
                    ) else typeDropMenuAutoCompleteTextView.setText(lure.type.name)
                    if (lure.divingDepth == null || lure.divingDepth.name == "UNKNOWN") divingDepthDropMenuAutoCompleteTextView.setText(
                        ""
                    ) else divingDepthDropMenuAutoCompleteTextView.setText(lure.divingDepth.name)
                    if (lure.floatation == null || lure.floatation.name == "UNKNOWN") floatationDropMenuAutoCompleteTextView.setText(
                        ""
                    ) else floatationDropMenuAutoCompleteTextView.setText(lure.floatation.name)
                    lure.weight.let {
                        if (it != null) {
                            weightEditText.setText(it.toString())
                        } else {
                            weightEditText.setText("")
                        }
                    }
                    lure.length.let {
                        if (it != null) {
                            lengthEditText.setText(it.toString())
                        } else {
                            lengthEditText.setText("")
                        }
                    }
                    lure.description.let { descriptionEditText.setText(it) }
                    lure.color.let { colorEditText.setText(it) }
                    lure.effectiveness.toString().let { caughtFishEditText.setText(it) }
                    lure.notes.let { notesEditText.setText(it) }
                    lure.imageUrl.let {
                        if (it != null) {
                            currentLurePhotoGroup.visibility = View.VISIBLE
                            uri = it.toUri()
                            currentLureImageView.setImageURI(it.toUri())
                        } else {
                            currentLurePhotoGroup.visibility = View.GONE
                        }
                    }
                }
            }
        }

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
                id = lureId,
                name = nameEditText.text.toString(),
                manufacturer = manufacturerEditText.text.toString(),
                type = convertLureTypeCategory(typeDropMenuAutoCompleteTextView.text.toString()),
                divingDepth = convertLureDivingDepthCategory(divingDepthDropMenuAutoCompleteTextView.text.toString()),
                floatation = convertLureFloatationCategory(floatationDropMenuAutoCompleteTextView.text.toString()),
                weight = weightEditText.text.toString().toIntOrNull(),
                length = lengthEditText.text.toString().toIntOrNull(),
                description = descriptionEditText.text.toString(),
                color = colorEditText.text.toString(),
                effectiveness = if (caughtFishEditText.text.isNullOrEmpty()) 0 else caughtFishEditText.text.toString()
                    .toInt(),
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