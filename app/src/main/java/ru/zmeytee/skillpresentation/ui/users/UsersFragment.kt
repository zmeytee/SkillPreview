package ru.zmeytee.skillpresentation.ui.users

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
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
import ru.zmeytee.skillpresentation.R
import ru.zmeytee.skillpresentation.data.adapters.UserAdapter
import ru.zmeytee.skillpresentation.data.enums.ItemAction
import ru.zmeytee.skillpresentation.databinding.FragmentUsersBinding
import ru.zmeytee.skillpresentation.ui.FabActionListener

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users) {

    private val viewModel by viewModels<UsersViewModel>()
    private val binding by viewBinding(FragmentUsersBinding::bind)
    private var userAdapter: UserAdapter? = null
    private val fabActionListener: FabActionListener?
        get() = activity?.let { it as FabActionListener }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUsersList()
        setFragmentDefaults()
        bindViewModel()
    }

    private fun bindViewModel() {
        with(viewModel) {
            users
                .onEach { userAdapter?.items = it }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun setFragmentDefaults() {
        binding.usersTitle.text = getString(R.string.users_title)
        fabActionListener?.setFabAction(ItemAction.USER_ADD)
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

        viewModel.getListOfAllUsers()
    }

    private fun navigateToUserDetails(userId: Long) {
        val action = UsersFragmentDirections.actionUsersFragmentToUserDetailsFragment(userId)
        findNavController().navigate(action)
    }
}