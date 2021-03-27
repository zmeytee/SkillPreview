package ru.zmeytee.skillpresentation.data.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.zmeytee.skillpresentation.data.models.User

class UserAdapter(
    onClick: (id: Long) -> Unit
): AsyncListDifferDelegationAdapter<User>(UserDiffUtilCallback()) {

    init {
        delegatesManager
            .addDelegate(UserDelegate(onClick))
    }

    class UserDiffUtilCallback: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return when {
                oldItem is User.Remote && newItem is User.Remote -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

}