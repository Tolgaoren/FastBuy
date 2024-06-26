package com.toren.producthub.presentation.profile_update

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.toren.producthub.databinding.FragmentUpdateProfileBinding
import com.toren.producthub.domain.model.Address
import com.toren.producthub.domain.model.Bank
import com.toren.producthub.domain.model.Coordinates
import com.toren.producthub.domain.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateProfileFragment : Fragment() {

    private var _binding: FragmentUpdateProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpdateProfileViewModel by viewModels()
    private val args: UpdateProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateProfileBtn.setOnClickListener {
            observeResponse()
            val userUpdates = control()
            viewModel.updateUser(userUpdates)
        }
        args.user.let {
            loadUserDetails(it)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun control(): User {
        val usernameTxt = binding.updateProfileUsernameTxt.text.toString().trim()
        val firstNameTxt = binding.updateProfileFirstNameTxt.text.toString()
        val lastNameTxt = binding.updateProfileLastNameTxt.text.toString()
        val emailTxt = binding.updateProfileEmailTxt.text.toString()
        val phoneNumberTxt = binding.updateProfilePhoneNumberTxt.text.toString()
        val addressTxt = binding.updateProfileAddressTxt.text.toString()
        val cityTxt = binding.updateProfileCityTxt.text.toString()
        val stateTxt = binding.updateProfileStateTxt.text.toString()
        val postalCodeTxt = binding.updateProfilePostalCodeTxt.text.toString()
        val countryTxt = binding.updateProfileCountryTxt.text.toString()
        val creditCardNumberTxt = binding.updateProfileCreditCardNumberTxt.text.toString()

        val username = if (usernameTxt.length > 3 && args.user.username != usernameTxt) {
            usernameTxt
        } else {
            args.user.username
        }

        val firstName = if (firstNameTxt.length > 2 && args.user.firstName != firstNameTxt) {
            firstNameTxt
        } else {
            args.user.firstName
        }

        val lastName = if (lastNameTxt.length > 2 && args.user.lastName != lastNameTxt) {
            lastNameTxt
        } else {
            args.user.lastName
        }

        val email = if (emailTxt.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$"))
            && args.user.email != emailTxt) {
            emailTxt
        } else {
            args.user.email
        }

        val phoneNumber = if (phoneNumberTxt.length > 8 && args.user.phone != phoneNumberTxt) {
            phoneNumberTxt
        } else {
            args.user.phone
        }

        val address = if (addressTxt.length > 4 && args.user.address.address != addressTxt) {
            addressTxt
        } else {
            args.user.address.address
        }

        val city = if (addressTxt.length > 2 && args.user.address.city != cityTxt) {
            cityTxt
        } else {
            args.user.address.city
        }

        val state = if (stateTxt.length > 2 && args.user.address.state != stateTxt) {
            stateTxt
        } else {
            args.user.address.state
        }

        val postalCode = if (postalCodeTxt.length > 4 && args.user.address.postalCode != postalCodeTxt) {
            postalCodeTxt
        } else {
            args.user.address.postalCode
        }

        val country = if (countryTxt.length > 2 && args.user.address.country != countryTxt) {
            countryTxt
        } else {
            args.user.address.country
        }

        val creditCardNumber = if (creditCardNumberTxt.length == 16 && args.user.bank.cardNumber != creditCardNumberTxt) {
            creditCardNumberTxt
        } else {
            args.user.bank.cardNumber
        }


        return User(
            username = username,
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phoneNumber,
            address = Address(
                address = address,
                city = city,
                state = state,
                postalCode = postalCode,
                country = country,
                coordinates = Coordinates(0.0,0.0),
                stateCode = ""
            ),
            bank = Bank(
                cardNumber = creditCardNumber,
                cardType = "",
                currency = "",
                iban = "",
                cardExpire = ""
            ),
            birthDate = args.user.birthDate,
            id = args.user.id,
            image = args.user.image
        )
    }

    private fun loadUserDetails(user: User) {
        binding.apply {
            updateProfileUsernameTxt.setText(user.username)
            updateProfileFirstNameTxt.setText(user.firstName)
            updateProfileLastNameTxt.setText(user.lastName)
            updateProfileEmailTxt.setText(user.email)
            updateProfilePhoneNumberTxt.setText(user.phone)
            updateProfileAddressTxt.setText(user.address.address)
            updateProfileCityTxt.setText(user.address.city)
            updateProfileStateTxt.setText(user.address.state)
            updateProfilePostalCodeTxt.setText(user.address.postalCode)
            updateProfileCountryTxt.setText(user.address.country)
            updateProfileCreditCardNumberTxt.setText(user.bank.cardNumber)
        }
    }

    private fun observeResponse() {
        viewModel.user.observe(viewLifecycleOwner) { response ->
            response.data?.let {
                actionToProfile(it)
            }
        }
    }

    private fun actionToProfile(user: User) {
        val action = UpdateProfileFragmentDirections.actionUpdateProfileFragmentToNavProfile(user)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}