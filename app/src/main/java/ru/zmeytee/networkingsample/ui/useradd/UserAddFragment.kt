package ru.zmeytee.networkingsample.ui.useradd

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.CircleCropTransformation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.zmeytee.networkingsample.R
import ru.zmeytee.networkingsample.data.enums.ItemAction
import ru.zmeytee.networkingsample.data.models.Address
import ru.zmeytee.networkingsample.data.models.Company
import ru.zmeytee.networkingsample.data.models.Geo
import ru.zmeytee.networkingsample.data.models.User
import ru.zmeytee.networkingsample.databinding.FragmentUserAddBinding
import ru.zmeytee.networkingsample.ui.FabActionListener
import ru.zmeytee.networkingsample.utils.toast
import timber.log.Timber

@AndroidEntryPoint
class UserAddFragment : Fragment(R.layout.fragment_user_add) {

    private val viewModel by viewModels<UserAddViewModel>()
    private val binding by viewBinding(FragmentUserAddBinding::bind)
    private val fabActionListener: FabActionListener?
        get() = activity?.let { it as FabActionListener }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            Timber.d("RESULT = $imageUri")
            setAvatarImage(imageUri)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabActionListener?.setFabAction(ItemAction.BACK)
        bindViewModel()
        setListeners()
        setAvatarImage()
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

            addingSuccess
                .onEach { success -> success?.let { handleUserAddingResult(it) } }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun setListeners() {
        with(binding) {
            avatarEditView.avatarEditFab.setOnClickListener {
                getContent.launch("image/*")
            }

            contactsCard.contactsTitleTextView.setOnClickListener {
                showHiddenForms(
                    binding.contactsCard.contactsGroup,
                    binding.contactsCard.contactsSpoiler
                )
            }
            addressCard.addressTitleTextView.setOnClickListener {
                showHiddenForms(
                    binding.addressCard.addressGroup,
                    binding.addressCard.addressSpoiler
                )
            }
            companyCard.companyTitleTextView.setOnClickListener {
                showHiddenForms(
                    binding.companyCard.companyGroup,
                    binding.companyCard.companySpoiler
                )
            }
            saveUserFab.setOnClickListener { saveUser(getUserFromForms()) }
        }
    }

    private fun setAvatarImage(imageUri: Uri? = null) {
        with(binding.avatarEditView.avatarImage) {
            imageUri
                ?.let {
                    load(imageUri) {
                        transformations(CircleCropTransformation())
                        placeholder(R.drawable.ic_person)
                    }
                }
                ?: load(R.drawable.ic_person) {
                    transformations(CircleCropTransformation())
                }
        }
    }

    private fun showHiddenForms(view: View, imageView: ImageView) {
        val isVisible = view.isVisible
        val drawable = if (isVisible) {
            R.drawable.ic_arrow_right
        } else {
            R.drawable.ic_arrow_down
        }

        view.isVisible = isVisible.not()
        imageView.setImageDrawable(
            ResourcesCompat.getDrawable(resources, drawable, null)
        )
    }

    private fun saveUser(user: User) {
        viewModel.saveUser(user)
    }

    private fun getUserFromForms(): User {
        val name = binding.userInfoCard.nameEditText.text.toString()
        val userName = binding.userInfoCard.userNameEditText.text.toString()
        val email = binding.contactsCard.emailEditText.text.toString()
        val phone = binding.contactsCard.phoneEditText.text.toString()
        val website = binding.contactsCard.websiteEditText.text.toString()

        return User(
            id = 0,
            name = name,
            userName = userName,
            email = email,
            address = getAddressFromForms(),
            phone = phone,
            website = website,
            company = getCompanyFromForms()
        )
    }

    private fun getAddressFromForms(): Address {
        val addressStreet = binding.addressCard.streetEditText.text?.toString()
        val addressSuite = binding.addressCard.suiteEditText.text.toString()
        val addressCity = binding.addressCard.cityEditText.text.toString()
        val addressZipcode = binding.addressCard.zipcodeEditText.text.toString()

        return Address(
            street = addressStreet,
            suite = addressSuite,
            city = addressCity,
            zipcode = addressZipcode,
            geo = getGeoFromForms()
        )
    }

    private fun getGeoFromForms(): Geo {
        val geoLat = null
        val geoLng = null

        return Geo(
            lat = geoLat,
            lng = geoLng
        )
    }

    private fun getCompanyFromForms(): Company {
        val companyName = binding.companyCard.companyNameEditText.text.toString()
        val companyCatchPhrase = binding.companyCard.companyCatchPhraseEditText.text.toString()
        val companyBs = binding.companyCard.companyBsEditText.text.toString()

        return Company(
            name = companyName,
            catchPhrase = companyCatchPhrase,
            bs = companyBs
        )
    }

    private fun handleUserAddingResult(success: Boolean) {
        if (success) {
            toast("Пользователь добавлен")
            findNavController().navigateUp()
        } else {
            toast("Ошибка добавления")
        }
        viewModel.resetStateFlow()
    }

    private fun showLoading(show: Boolean) {
        with(binding) {
            saveUserFab.isEnabled = !show

            //Avatar
            avatarEditView.avatarEditFab.isEnabled = !show
            //User info
            userInfoCard.nameEditText.isEnabled = !show
            userInfoCard.userNameEditText.isEnabled = !show
            //Contacts
            contactsCard.contactsTitleTextView.isEnabled = !show
            contactsCard.emailEditText.isEnabled = !show
            contactsCard.phoneEditText.isEnabled = !show
            contactsCard.websiteEditText.isEnabled = !show
            //Address
            addressCard.addressTitleTextView.isEnabled = !show
            addressCard.streetEditText.isEnabled = !show
            addressCard.suiteEditText.isEnabled = !show
            addressCard.cityEditText.isEnabled = !show
            addressCard.zipcodeEditText.isEnabled = !show
            addressCard.geoEditText.isEnabled = !show
            //Company
            companyCard.companyTitleTextView.isEnabled = !show
            companyCard.companyNameEditText.isEnabled = !show
            companyCard.companyCatchPhraseEditText.isEnabled = !show
            companyCard.companyBsEditText.isEnabled = !show

            loadingProgress.root.isVisible = show
        }
    }
}