package ru.zmeytee.skillpreview.data.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.zmeytee.skillpreview.R
import ru.zmeytee.skillpreview.data.models.User
import ru.zmeytee.skillpreview.databinding.ItemUserBinding
import ru.zmeytee.skillpreview.utils.inflate

class UserDelegate(
    private val onClick: (id: Long) -> Unit
) : AbsListItemAdapterDelegate<User.Remote, User, UserDelegate.Holder>() {

    override fun isForViewType(item: User, items: MutableList<User>, position: Int): Boolean {
        return item is User.Remote
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            ItemUserBinding.bind(parent.inflate(R.layout.item_user)),
            onClick
        )
    }

    override fun onBindViewHolder(
        item: User.Remote,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        private val binding: ItemUserBinding,
        private val onClick: (id: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentUserId: Long? = null

        init {
            binding.root.setOnClickListener {
                currentUserId?.let { onClick(it) }
            }
        }

        fun bind(item: User.Remote) {
            currentUserId = item.id

            with(binding) {
                userItemName.text = item.name
                userItemUserName.text = item.userName
            }
        }
    }
}