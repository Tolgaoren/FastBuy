package com.toren.producthub.presentation.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.toren.producthub.R
import com.toren.producthub.databinding.FragmentProfileBinding
import com.toren.producthub.domain.model.User
import com.toren.producthub.utils.CreditCartNumberFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), MenuProvider {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private val args: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        if(args.userResponse!=null) {
            loadUser(args.userResponse!!)
        } else {
            viewModel.getUserInfo()
            observeUser()
        }
    }

    private fun observeUser() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            user.data?.let {
                loadUser(user.data)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadUser(user: User) {
        Glide.with(requireContext())
            .load(user.image)
            .into(binding.profileImg)
        binding.apply {
            profileUsernameTxt.text = user.username
            profileNameTxt.text = "${user.firstName} ${user.lastName}"
            profileBirthdayTxt.text = user.birthDate
            profileEmailTxtV.text = user.email
            profilePhoneNumberTxtV.text = user.phone
            profileAddressTxtV.text =
                "${user.address.address} ${user.address.city}, ${user.address.state} ${user.address.postalCode}, ${user.address.country}"
            profileCreditCardNumberTxtV.text = CreditCartNumberFormatter().formatCreditCardNumber(user.bank.cardNumber)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.profile, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_update_profile -> {
                actionUpdateProfile()
                return true
            } else -> return false
        }
    }

    private fun actionUpdateProfile() {
        val user = viewModel.user.value?.data
        user.let {
            val action = ProfileFragmentDirections.actionNavProfileToUpdateProfileFragment(user!!)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}