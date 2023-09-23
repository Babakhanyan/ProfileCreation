package com.example.profilecreation.ui.signUp

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.example.common.extenssion.addOnBackPressedCallback
import com.example.common.extenssion.collectWhileStarted
import com.example.common.extenssion.viewBinding
import com.example.profilecreation.R
import com.example.profilecreation.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    @Inject
    lateinit var navController: dagger.Lazy<NavController>

    private val viewModel: SignUpViewModel by viewModels()

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private lateinit var avatarUri: Uri

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                viewModel.updateAvatar(avatarUri = avatarUri.toString())
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.camera_was_close,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeSignUpView.setContent {
            MaterialTheme {
                SignUpScreen(viewModel)
            }
        }
        addOnBackPressedCallback(viewLifecycleOwner) { requireActivity().finish() }
        prepareAvatarUri()
        observeViewModel()
    }

    private fun observeViewModel() {
        with(viewModel) {
            command.collectWhileStarted(viewLifecycleOwner) {
                when (it) {
                    is Command.OpenConfirmationPage -> {
                        navController.get().navigate(R.id.confirmationFragment)
                    }
                    Command.OpenCamera -> takePicture.launch(avatarUri)
                }
            }
        }
    }

    private fun prepareAvatarUri() {
        avatarUri = requireActivity().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            ContentValues()
        ) ?: error("Uri is null")
    }
}