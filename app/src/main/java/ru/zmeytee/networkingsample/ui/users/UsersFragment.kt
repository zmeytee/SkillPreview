package ru.zmeytee.networkingsample.ui.users

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.zmeytee.networkingsample.R
import ru.zmeytee.networkingsample.data.adapters.UserAdapter
import ru.zmeytee.networkingsample.data.enums.ItemAction
import ru.zmeytee.networkingsample.databinding.FragmentUsersBinding
import ru.zmeytee.networkingsample.ui.FabActionListener

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users) {

    private val viewModel by viewModels<UsersViewModel>()
    private val binding by viewBinding(FragmentUsersBinding::bind)
    private var userAdapter: UserAdapter? = null
    private val fabActionListener: FabActionListener?
        get() = activity?.let { it as FabActionListener }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabActionListener?.setFabAction(ItemAction.USER_ADD)
        viewModel.getListOfAllUsers()
        initUsersList()
        bindViewModel()
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

            users
                .onEach { userAdapter?.items = it }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun initUsersList() {
        userAdapter = UserAdapter(::navigateToUserDetails)

        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)

        with(binding.usersList) {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
            drawable?.let {
                dividerItemDecoration.setDrawable(it)
                addItemDecoration(dividerItemDecoration)
            }
            setHasFixedSize(true)
        }
    }

    private fun showLoading(show: Boolean) {
        binding.usersLoading.root.isVisible = show
    }

    private fun navigateToUserDetails(userId: Long) {
        val action = UsersFragmentDirections.actionUsersFragmentToUserDetailsFragment(userId)
        findNavController().navigate(action)
    }
}