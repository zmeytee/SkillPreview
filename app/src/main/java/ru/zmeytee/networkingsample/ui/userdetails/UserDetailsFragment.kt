package ru.zmeytee.networkingsample.ui.userdetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.CircleCropTransformation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.zmeytee.networkingsample.R
import ru.zmeytee.networkingsample.data.enums.ItemAction
import ru.zmeytee.networkingsample.data.models.User
import ru.zmeytee.networkingsample.databinding.FragmentUserDetailsBinding
import ru.zmeytee.networkingsample.ui.FabActionListener
import ru.zmeytee.networkingsample.utils.toast

@AndroidEntryPoint
class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private val viewModel by viewModels<UserDetailsViewModel>()
    private val binding by viewBinding(FragmentUserDetailsBinding::bind)
    private val args by navArgs<UserDetailsFragmentArgs>()
    private val fabActionListener: FabActionListener?
        get() = activity?.let { it as FabActionListener }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabActionListener?.setFabAction(ItemAction.BACK)
        bindViewModel()
        setListeners()
        getUserDetails(args.userId)
    }

    override fun onDestroyView() {
        viewModel.cancelJob()
        super.onDestroyView()
    }

    private fun bindViewModel() {
        with(viewModel) {
            isLoading
                .onEach { showLoading(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            deletingSuccess
                .onEach { success -> success?.let { handleUserDeletingResult(it) } }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            currentUser
                .onEach { handleUserDetails(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun setListeners() {
        with(binding) {
            deleteUserFab.setOnClickListener { viewModel.deleteUser(args.userId) }
        }
    }

    private fun getUserDetails(id: Long) {
        viewModel.getUser(id)
    }

    private fun handleUserDetails(user: User?) {
        if (user == null) return
        showUserDetails(user)
    }

    private fun showUserDetails(user: User) {
        with(binding) {
            userDetailsCard.userItemUserName.text = user.userName
            userDetailsCard.userItemName.text = user.name

            userDetailsEmail.text = user.email
            userDetailsWebsite.text = user.website
            userDetailsPhone.text = user.phone
            userDetailsAddress.text = user.address?.street
            userDetailsAddressDescription.text = user.address?.city
            userDetailsCompany.text = user.company?.name

            userDetailsCard.userItemAvatar.load("https://www.meme-arsenal.com/memes/ad998282fd526298aeb217a8e2ee02b0.jpg") {
                placeholder(R.drawable.ic_person)
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun handleUserDeletingResult(success: Boolean) {
        if (success) {
            toast("Пользователь удален")
            findNavController().navigateUp()
        } else {
            toast("Ошибка удаления")
        }
        viewModel.resetStateFlow()
    }

    private fun showLoading(show: Boolean) {
        binding.deleteUserFab.isEnabled = !show
        binding.userDetailsLoading.root.isVisible = show
    }
}