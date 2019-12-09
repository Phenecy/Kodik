package dev.bonch.kodik.fragments

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import dev.bonch.kodik.R

private lateinit var profileData: TextView
private lateinit var profileMyTrophy: TextView
private lateinit var profileName: TextView
private lateinit var profileImageView: ImageView
private lateinit var mDatabase : FirebaseDatabase
private lateinit var mReference : DatabaseReference
private lateinit var imageUri : Uri
private lateinit var profileChangePhoto: TextView

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val profileTrophyFragment = ProfileTrophyFragment()
        val profileDataFragment = ProfileDataFragment()

        profileData = view.findViewById(R.id.profile_data)
        profileMyTrophy = view.findViewById(R.id.profile_my_trophy)
        profileName = view.findViewById<TextView>(R.id.profile_name)
        profileImageView = view.findViewById(R.id.profile_photo)

        Glide.with(view)
            .load("https://firebasestorage.googleapis.com/v0/b/kodik-ea6fa.appspot.com/o/profile_ava.png?alt=media&token=56a4ce99-e66b-4624-863c-4bfb33153cd1")
            .apply(RequestOptions.circleCropTransform())
            .into(profileImageView)

        fragmentManager!!.beginTransaction()
            .add(R.id.profile_container, profileTrophyFragment)
            .commit()

        fun toDataFragment() {
            profileData.setTextColor(Color.parseColor("#000000"))
            profileMyTrophy.setTextColor(Color.parseColor("#575757"))
            fragmentManager!!.beginTransaction()
                .replace(R.id.profile_container, profileDataFragment)
                .commit()
        }

        fun toTrophyFragment() {
            profileData.setTextColor(Color.parseColor("#575757"))
            profileMyTrophy.setTextColor(Color.parseColor("#000000"))
            fragmentManager!!.beginTransaction()
                .replace(R.id.profile_container, profileTrophyFragment)
                .commit()
        }

        fun setUserName() {
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                profileName.text = user.displayName
            }
        }

        setUserName()

        profileData.setOnClickListener {
            toDataFragment()
        }

        profileMyTrophy.setOnClickListener {
            toTrophyFragment()
        }
        return view
    }

    fun setUserData() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            profileName.text = user.displayName
        }
    }
}