package com.example.riyadal_qulub.ui.authentication

import AuthViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.riyadal_qulub.databinding.FragmentSignUpBinding
import com.example.riyadal_qulub.ui.home.HomeActivity

private const val TAG = "SignUpFragment"

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewmodel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.setOnClickListener {
            val email = binding.emailEt.editText?.text.toString()
            val password = binding.passwordEt.editText?.text.toString()


            val isValid = validateInputs(email, password)

            if (isValid) {
                // Clear previous error messages
                binding.emailEt.error = null
                binding.emailEt.error = null

                viewmodel.signIn(email, password)
            }

        }

        // Observe the sign-up error message
        viewmodel.signUpError.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewmodel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        Intent(requireActivity(), HomeActivity::class.java).also {
            startActivity(it)
            requireActivity().finish()
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        var isValid = true

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEt.error = "لا يمكن أن يكون البريد الإلكتروني فارغًا"
            isValid = false
        }

        if (password.isEmpty()) {
            binding.passwordEt.error = "لا يمكن أن تكون كلمة المرور فارغة"
            isValid = false
        }

        return isValid
    }
}