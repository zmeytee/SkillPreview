package ru.zmeytee.skillpresentation.ui

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.zmeytee.skillpresentation.R
import ru.zmeytee.skillpresentation.data.enums.ItemAction
import ru.zmeytee.skillpresentation.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), FabActionListener {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onNavigateUp(): Boolean {
        return findNavController(R.id.navHostFragment).navigateUp()
    }

    override fun onBackPressed() {
        if (onNavigateUp().not()) super.onBackPressed()
    }

    override fun setFabAction(action: ItemAction) {
        when (action) {
            ItemAction.USER_ADD -> changeNavigationFabAction(R.drawable.ic_person_add)
            ItemAction.BACK -> changeNavigationFabAction(R.drawable.ic_arrow_back)
        }
    }

    private fun changeNavigationFabAction(@DrawableRes icon: Int, @IdRes navDirection: Int? = null) {
        with(binding.navigationFab) {
            setImageDrawable(ContextCompat.getDrawable(this@MainActivity, icon))

            setOnClickListener {
                navDirection
                    ?.let { findNavController(R.id.navHostFragment).navigate(navDirection) }
                    ?: findNavController(R.id.navHostFragment).navigateUp()
            }
        }
    }

}