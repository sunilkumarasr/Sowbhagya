package com.biotech.sowbhagyabiotech.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.authentication.LoginActivity
import com.biotech.sowbhagyabiotech.authentication.LoginViewModel
import com.biotech.sowbhagyabiotech.databinding.AccountScreenBinding
import com.biotech.sowbhagyabiotech.roomdb.CartItems
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.utils.CommonMethods


class AccountFragment : Fragment() {

    private var _binding: AccountScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var profileViewModel: LoginViewModel
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper

    var cartData: List<CartItems> = ArrayList<CartItems>()
    lateinit var productIDStr: String
    lateinit var productQtyStr: String
    lateinit var address: String
    lateinit var username: String
    lateinit var category_id: String
    lateinit var customerid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = AccountScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(requireActivity(), "sowbhagya")
        profileViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        addObservers()
        CommonMethods.showProgress(requireContext())
        profileViewModel.callgetProfileApi(
            requireContext(), securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, "")
        )
        binding.editprofileLL.paintFlags =
            binding.editprofileLL.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        binding.editprofileLL.setOnClickListener {

            findNavController().navigate(
                R.id.navigation_profile
            )
        }

        binding.wishListLL.setOnClickListener {

            findNavController().navigate(
                R.id.navigation_wishlist
            )
        }
        binding.locationsLL.setOnClickListener {
            startActivity(Intent(requireActivity(), MyAddressActivity::class.java))
        }
        binding.myordersLL.setOnClickListener {

            findNavController().navigate(
                R.id.navigation_orders
            )

        }
        binding.shippingLl.setOnClickListener {
            startActivity(Intent(requireActivity(), ShippingPolicyActivity::class.java))
        }
        binding.refundLl.setOnClickListener {
            startActivity(Intent(requireActivity(), ReturnPolicyActivity::class.java))
        }
        binding.termsCoditionsLL.setOnClickListener {
            startActivity(Intent(requireActivity(), TermsAndConditionsActivity::class.java))
        }
        binding.privacyPolicyLL.setOnClickListener {
            startActivity(Intent(requireActivity(), ProivacyPolicyActivity::class.java))
        }
        binding.lnrWhatsapp.setOnClickListener {
            val uri = Uri.parse(requireActivity().getString(R.string.whatasapp))
            val i = Intent(Intent.ACTION_SENDTO, uri)
            i.setPackage("com.whatsapp")
            startActivity(Intent.createChooser(i, ""))
            /*val pm: PackageManager = requireActivity().packageManager
            try {
                val waIntent = Intent(Intent.ACTION_SEND)
                waIntent.type = "text/plain"
                val text = "Sowbhagya: "
                val info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
                //Check if package exists or not. If not then code
                //in catch block will be called
                waIntent.setPackage("com.whatsapp")
                waIntent.putExtra(Intent.EXTRA_TEXT, text)
                startActivity(Intent.createChooser(waIntent, "Share with"))
            } catch (e: NameNotFoundException) {
                Toast.makeText(requireActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show()
            }*/
        }
        binding.logoutLL.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Logout")
            builder.setMessage("Are you sure want to Logout?")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                securedSharedPreferenceWrapper.clear()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->

                dialog.dismiss()
            }


            builder.show()

        }
        binding.contactUsLL.setOnClickListener {
            Intent(requireActivity(), ContactUsActivity::class.java).also {
                startActivity(it)
            }
        }

        /*try {

            val fullnames = securedSharedPreferenceWrapper.getString("fullname", "").toString()
            val mnum = securedSharedPreferenceWrapper.getString("mnum", "").toString()
            val hn = securedSharedPreferenceWrapper.getString("hn", "").toString()
            val lmark = securedSharedPreferenceWrapper.getString("lmark", "").toString()
            val loc = securedSharedPreferenceWrapper.getString("loc", "").toString()
            val cit = securedSharedPreferenceWrapper.getString("cit", "").toString()
            val stat = securedSharedPreferenceWrapper.getString("stat", "").toString()
            val pin = securedSharedPreferenceWrapper.getString("pin", "").toString()


            if (hn.isEmpty()) {
                //binding.adressdata.text = "Choose Address"

            } else {
                binding.tvAddressDetails.text =
                    (hn + "," + lmark + "," + loc + "," + cit + "," + stat + "," + pin + ".")

            }



        } catch (e: Exception) {
            e.printStackTrace()
        }*/

        return root
    }

    private fun addObservers() {

        profileViewModel.getprofileModelResponse.observe(requireActivity()) {

            if (it.Status == true) {
                CommonMethods.hideProgress()
                binding.nameTV.text = "" + it.Response?.name
                binding.mobileTV.text = "" + it.Response?.mobileNumber
                binding.emailTV.text = "" + it.Response?.emailId
                Glide.with(requireActivity()).load(it.Response?.profile_image_full_url)
                    .placeholder(R.drawable.baseline_account_circle_24)
                    .into(binding.profileIMG)
                binding.tvAddressDetails.text = ""+it.Response?.hno+", "+it.Response?.landmark+", "+it.Response?.locality+", "+it.Response?.city+", "+it.Response?.state+", "+it.Response?.pincode+"."

            } else {
                CommonMethods.hideProgress()
                Toast.makeText(requireActivity(), "" + it.Message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as Home_Activity).setHeaderTitleVisible("Profile")
    }

    override fun onPause() {
        super.onPause()
        (activity as Home_Activity).setHeaderTitleGone()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}