package com.example.profilecreation.ui.confirmation

import android.os.Bundle
import android.view.View
import androidx.compose.material3.MaterialTheme
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.example.common.extenssion.addOnBackPressedCallback
import com.example.common.extenssion.hideKeyboard
import com.example.common.extenssion.viewBinding
import com.example.profilecreation.R
import com.example.profilecreation.databinding.FragmentConfirmationBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ConfirmationFragment : Fragment(R.layout.fragment_confirmation) {

    @Inject
    lateinit var navController: dagger.Lazy<NavController>

    private val viewModel: ConfirmationViewModel by viewModels()

    private val binding by viewBinding(FragmentConfirmationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeConfirmationView.setContent {
            MaterialTheme {
                ConfirmationScreen(viewModel)
            }
        }
        hideKeyboard()
        addOnBackPressedCallback(viewLifecycleOwner) { navController.get().popBackStack() }
    }
}