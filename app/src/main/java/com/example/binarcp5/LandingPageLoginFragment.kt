package com.example.binarcp5

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment

class LandingPageLoginFragment : Fragment() {

    lateinit var etName : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_landing_page_login, container, false)

        etName = view.findViewById(R.id.et_name)

        etName.addTextChangedListener {
            // step 6 kirim data dari fragment ke activity, kirim datanya dengan panggil method
            listener?.afterUserInputName(it.toString())
        }


        // Inflate the layout for this fragment
        return view
    }

    // step 2 kirim data dari fragment ke activity, bikin variable interface fragment
    var listener: UserNameInputListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // step 5 kirim data dari fragment ke activity, init variable listener
        if (context is UserNameInputListener) {
            listener = context
        }
    }

    // step 1 kirim data dari fragment ke activity, bikin interface di fragment
    interface UserNameInputListener {
        fun afterUserInputName(input: String)
    }

}