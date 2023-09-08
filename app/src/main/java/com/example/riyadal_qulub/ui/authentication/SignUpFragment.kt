package com.example.riyadal_qulub.ui.authentication

import AuthViewModel
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.example.riyadal_qulub.R
import com.example.riyadal_qulub.databinding.FragmentSignUpBinding
import com.example.riyadal_qulub.ui.home.HomeActivity
import com.example.riyadal_qulub.utils.Constant

private const val TAG = "SignUpFragment"

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewmodel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(LayoutInflater.from(context), container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignIn.setOnClickListener {
            val name = binding.nameEt.editText?.text.toString()
            val email = binding.emailEt.editText?.text.toString()
            val password = binding.passwordEt.editText?.text.toString()

            val isValid = validateInputs(name, email, password)

            if (isValid) {
                // Clear previous error messages
                binding.nameEt.error = null
                binding.emailEt.error = null
                binding.passwordEt.error = null

                viewmodel.signUp(email, password)
                saveUser(name)
            }


        }

        // Observe the sign-up error message
        viewmodel.signUpError.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                // Display the error message inside the TextInputLayout
                binding.emailEt.error = it
            }
        }

        viewmodel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                // User is signed up, navigate to the next screen
                Intent(requireActivity(), HomeActivity::class.java).also {
                    startActivity(it)
                    requireActivity().finish()
                }
            }
        }
    }

    private fun saveUser(name: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(Constant.nameSharedPref, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString(Constant.nameStringSharedPref, name)
        }.apply()

    }

    private fun validateInputs(name: String, email: String, password: String): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            binding.nameEt.error = "الرجاء ادخال الاسم"
            isValid = false
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.nameEt.error = "هذا البريد الإلكتروني غير صالح"
            isValid = false
        }

        if (password.isEmpty() || password.length < 6) {
            binding.passwordEt.error = "كلمة المرور يجب أن تكون أكثر من 6 أحرف"
            isValid = false
        } else if (password != binding.confirmPasswordEt.editText?.text.toString()) {
            binding.confirmPasswordEt.error = "كلمة المرور غير متطابقة"
            isValid = false
        }

        return isValid
    }
}
